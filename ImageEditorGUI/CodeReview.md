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
- There are some public functions in the GUI which allow easy additional inputs from the user if
  needed. However, these functions are quite operations specific. Writing a more general method to
  get these inputs may be helpful when adding more operations.
- Also, the decision to use buttons for each operation make it a little difficult to add any new
  operations ot the GUI.

## Implementation Critique

- How good is the quality of the code and how well does it adhere to best practices and coding
  standards?
- The code is formatted well and the java style of the received code adheres to the standards set in
  this course.
- Variable names were descriptive and succinct.
- How efficient, scalable, and error-free is the code?
- The controller and model are quite scalable. There are very few lines which need to be changed
  when adding a new operation.
- Are there any performance bottlenecks or security vulnerabilities?

## Documentation Critique

- The Documentation provided is clear and concise
- All public facing functions are well documented and required little or no input to understand
- All classes and interfaces javadocs were descriptive.
- Private functions also had comments, making them easy to use if needed.
- The readme and useme files are well written and are describe the working of the code well.

## Design/Code Strengths

- The design of the model is very strong. With a well modularized code, it is easy to extends and
  add features to it.
- It also accounts for multiple images to be passed with a variable number of command line arguments
  allowing for a more diverse set of function that can be added without changing or extending the
  model interface.

## Design/Code Limitations

- In the view, since every operation has its own button, it will get cumbersome to add many more new
  features.
-

## Suggestions for Improvement

- Reduce the amount of buttons on the GUI, possibly with a dropdown menu to list all the supported
  operations.
- Make the methods for additional inputs in the GUI more general, for example the method for getting
  the brightness value might be more useful as just a general get value method which you can set the
  text for. 

