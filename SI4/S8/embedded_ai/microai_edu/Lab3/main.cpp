#include <algorithm>
#include <array>
#include <cstring>
#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>

#include "model.h"

template<int N>
std::vector<std::array<float, N>> readInputsFromFile(const char *filename) {
	// Read training vectors from CSV file
	std::vector<std::array<float, N>> inputs;
	std::ifstream fin(filename);
	if (!fin) {
		std::cerr << "Error opening \"" << filename << "\": " << strerror(errno) << std::endl;
		exit(0);
	}
	std::string linestr;
	while (std::getline(fin, linestr)) {
		std::istringstream linestrs(linestr);
		std::string floatstr;
		std::array<float, N> floats;
		for (int i = 0; std::getline(linestrs, floatstr, ','); i++) {
			floats.at(i) = std::strtof(floatstr.c_str(), NULL);
		}
		inputs.push_back(floats);
	}
	return inputs;
}

template<size_t Channels, size_t Samples>
void convert_input_vector(const std::array<float, Channels*Samples> &input, number_t out[Channels][Samples]) {
	for (size_t i = 0; i < Channels; i++) {
		for (size_t j = 0; j < Samples; j++) {
			out[i][j] = clamp_to_number_t((long_number_t)(input.at(j*Channels + i) * (1<<FIXED_POINT))); // Warning: exchanges channels and samples dimensions
		}
	}
}

//Compute testing accuracy
template<size_t InputDims, size_t OutputDims>
float evaluate(const std::vector<std::array<float, InputDims>> &inputs, const std::vector<std::array<float, OutputDims>> &labels) {
	int rightlabels = 0;
	std::array<number_t, OutputDims> outputs = {};

	for (size_t i = 0;  i < inputs.size() && i < labels.size(); i++) {
		number_t converted_input[MODEL_INPUT_CHANNELS][MODEL_INPUT_SAMPLES];

		convert_input_vector<MODEL_INPUT_CHANNELS, MODEL_INPUT_SAMPLES>(inputs.at(i), converted_input);
		cnn(converted_input, outputs.data());

		auto cls = std::max_element(outputs.begin(), outputs.end()) - outputs.begin();

		if (labels.at(i).at(cls) > 0) {
			rightlabels++;
		}
	}
	return rightlabels/(float)inputs.size();
}

int main(int argc, const char *argv[]) {
	if (argc != 3) {
		std::cerr << "Usage: " << argv[0] << " testX.csv testY.csv" << std::endl;
		exit(1);
	}

	auto inputs = readInputsFromFile<MODEL_INPUT_SAMPLES*MODEL_INPUT_CHANNELS>(argv[1]);
	auto labels = readInputsFromFile<MODEL_OUTPUT_SAMPLES>(argv[2]);

	auto acc = evaluate(inputs, labels);

	std::cerr << "Testing accuracy: " << acc << std::endl;

	return 0;
}
