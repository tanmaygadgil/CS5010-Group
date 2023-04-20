# Code Review for [Shubh Desai](mailto:desai.shu@northeastern.edu) and [Pushkar Kurhekar](mailto:kurhekar.p@northeastern.edu)

## Design Critique

- The design overall is very modular and easy to extends.
- The model is designed well. Each of the operations has been given its own class and extends a
  common
  abstract class. this allows for increased extensibility.
- The text controller follows the command design pattern, and it is fairly easy to add new commands
  to the model.
- The controller for the GUI and the view are very heavily coupled though.
- The GUI can use more modularity. All of the components are implemented in a single java class
  which gives it too much responsibility.
- Also, the decision to use buttons for each operation make it a little difficult to add any new
  operations ot the GUI.

## Implementation Critique

- How good is the quality of the code and how well does it adhere to best practices and coding
  standards?
- How efficient, scalable, and error-free is the code?
- Are there any performance bottlenecks or security vulnerabilities?

## Documentation Critique

- The Documentation provided is clear and concise
- All public facing functions are well documented and required little or no input to understand
- The readme and useme files are well written and are describe the working of the code well.

## Design/Code Strengths

- The design of the model is very strong. With a well modularized code, it is easy to extends and
  add features to it.
- It also accounts for multiple images to be passed with a variable number of command line arguments
  allowing for a more diverse set of function that can be added without changing or extending the
  model interface.

## Design/Code Limitations

- What are the weaknesses or limitations of the design and/or code?
- Are there any design or implementation flaws that need to be addressed?
- What are some examples of poorly designed or implemented features?

## Suggestions for Improvement

- What are some specific changes or improvements that can be made to the design and/or code?
- Are there any new features or functionalities that can be added to enhance the project?
- What are some recommendations for improving the documentation or user experience?
