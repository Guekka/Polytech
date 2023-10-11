from dataclasses import dataclass, field
from typing import Any, List
import numpy as np
import tkinter as tk
from sklearn import datasets
from sklearn.linear_model import LogisticRegression

CANVAS_WIDTH = 750
CANVAS_HEIGHT = 80
NB_DIGITS = 10

ImageArray = np.ndarray[np.uint8, np.dtype[np.uint8]]

@dataclass
class Point:
    x: int = 0
    y: int = 0

@dataclass
class DrawnDigit:
    points: List[Point] = field(default_factory=list)


class Input:
    digits: List[DrawnDigit] = field(default_factory=list)

    def reset(self):
        self.digits = [DrawnDigit([]) for _ in range(NB_DIGITS)]

@dataclass
class PreprocessedDigit:
    image: ImageArray

def get_input_gui():
    """
    Using tkinter, let the user draw a number on a canvas.
    Return the drawn image as a list of points.
    """

    result = Input()

    master = tk.Tk()

    # prepare canvas
    canvas = tk.Canvas(master, width=CANVAS_WIDTH, height=CANVAS_HEIGHT, bg="white", cursor="cross")
    canvas.pack()

    label = tk.Label(master, text="Draw a number between 0 and 9")
    label.pack()

    # Done button
    button = tk.Button(master, text="Done", command=master.destroy)
    button.pack()

    # Reset button
    def reset():
        canvas.delete("all")
        result.reset()

        # draw lines to separate the canvas in 10 parts
        for i in range(1, NB_DIGITS):
            canvas.create_line(i * CANVAS_WIDTH / NB_DIGITS, 0, i * CANVAS_WIDTH / NB_DIGITS, CANVAS_HEIGHT, width=3)

    reset()

    reset_button = tk.Button(master, text="Reset", command=reset)
    reset_button.pack()

    def identify_canvas(event: Any) -> int:
        return int(event.x / (CANVAS_WIDTH / NB_DIGITS))

    # Create a function to add a point to the list and draw a line
    def add_point(event: Any):
        points = result.digits[identify_canvas(event)].points

        if len(points) > 0:
            canvas.create_line(points[-1].x, points[-1].y, event.x, event.y, width=3)

        points.append(Point(event.x, event.y))

    # Bind the function to the canvas
    canvas.bind("<B1-Motion>", add_point)

    # Run the mainloop
    tk.mainloop()

    return result

def prepare_model() -> LogisticRegression:
    X, y = datasets.load_digits(return_X_y=True)

    # Create a model
    model = LogisticRegression()

    # Train the model
    model.fit(X, y)

    return model

def preprocess_digit(digit: DrawnDigit) -> PreprocessedDigit:
    if len(digit.points) == 0:
        return PreprocessedDigit(np.zeros((8, 8), dtype=np.uint8))

    # Remove the white space around the digit
    min_x = min(point.x for point in digit.points)
    max_x = max(point.x for point in digit.points)
    min_y = min(point.y for point in digit.points)
    max_y = max(point.y for point in digit.points)

    # Create a new image
    image = np.zeros((max_y - min_y + 1, max_x - min_x + 1), dtype=np.uint8)

    # Fill the image
    for point in digit.points:
        image[point.y - min_y, point.x - min_x] = 255

    # Resize the image
    image = np.resize(image, (8, 8))

    # Flatten the image
    image = image.flatten()

    return PreprocessedDigit(image)


def recognize_digit(digit: DrawnDigit, model: LogisticRegression) -> int:
    """
    Given a drawn digit, return the predicted digit.
    """

    # Preprocess the digit
    image = preprocess_digit(digit)

    # Predict the digit
    return model.predict([image.image])[0]


def main():
    """
    Main function.
    """

    # Get the input image
    image = get_input_gui()

    # Prepare the model
    model = prepare_model()

    # Predict the digit
    for digit in image.digits:
        print(recognize_digit(digit, model))

if __name__ == "__main__":
    main()