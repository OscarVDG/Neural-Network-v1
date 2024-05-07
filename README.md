# Neural-Network
## Description
This was an attempt by me to create a neural network for the first time. I did it to deepen my understanding of gradient descent and backpropagation. This was necessary for my Extended Essay in Mathematics. (Typ gymnaisearbetet i Sverige.) I made this back in spring 2021 before my last year of highschool.

## Content
This is an attempt at a Neural Network from scratch. I coded my own matrix class. All the math behind the backpropagation I implemented and (at the time) understood myself. I think that it in essence worked as it should. I mainly used the following sources to learn and understand the math and concept behind gradient descent in Neural Networks:

**3B1B NN series on YT, episode 1-4:** [Here](https://www.youtube.com/playlist?list=PLZHQObOWTQDNU6R1_67000Dx_ZCJB-3pi)  
**Book on NNs by Michael Nielsen:** [Here](http://neuralnetworksanddeeplearning.com/)

I origianlly wanted to experiment with it by letting it solve chess puzzles. I evidently never got that far but I did implement some things while trainging the different networks.

## Results
I managed to train it to mimic and XOR-gate. If I remember correctly it managed to mimic some simple mathematical functions as well. Trying to mimic a sine function was much harder. However, the gradient descent seemed to worked. What I mean by that is that it did manage to find a pretty "deep" local minima for the error function. Through school, I managed to talk to a mathematician with a PhD in Neural Networks who said that it was probably due to me only using ReLU. She never saw any of my code though so I don't know who knows.

## Conclusion
I wanted to make a very general implementation where I could create any network in run-time. I never implemented data saving. It turned out alright. I made the creating of a network very flexible as in being able to change the number of layers and nodes per layer. However, I only implemented leaky ReLU which didn't turn out great in most cases. It basically couldn't learn anything non-linear. 

I didn't have time then to improve it and I'm now leaving it here as a finished attempt, however, in the future I intend (and will probably be required) to create other Neural Networks. I really wanted to understand how they worked from the ground up. When I eventually try that again I might use this as a starting point.
