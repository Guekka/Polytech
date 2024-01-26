function decodeUplink(input) {
  if (input.bytes.length == 1) {
    var counter = input.bytes[0];
  return {
    data: {
      counter: counter,
    },
    warnings: [],
    errors: []
  };
  }
}
