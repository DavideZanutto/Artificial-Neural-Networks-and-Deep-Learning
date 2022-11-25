## Class weights
[local_test, codalab]
- Manual: [0.6828, 0.69]
- sklearn class_weight: [0.6141]

## Activation functions
- relu [0.6828, 0.69]
- leaky_relu [0.60]

## Batch normalization
- without[0.6828]
- with [0.6343] (overfit)
  - [0.6768]
  - with augmentation [0.73, ]
  - two hidden layers [0.75]


## Text recap
We started by using the model from lab #5 as a base 
for our custom CNN. This first attempt bring us around 0.3 accurancy
on codalab.  
We've also tried to build the network from the ground up, starting with fewer layers (just 3)
and fewer parameters (500k vs 2M) and we obtained an impressive (considered the smaller size of the network)
0.6 for accuracy.  
Unfortunately, while trying to optimize this custom network we found out that simply adding layers or increasing
the size of the filters was having a huge (negative) impact on the accuracy, given the small sample size for the input,
our network tends to overfit quite quickly.
So we went back to our first design and tried to optimized more.  
We used keras-tuner to tune filter sizes, dropout layers, activation functions and even the number of hidden layers
in the fully connected network. Some results came out but nothing compared with what we gained by using transfer learning (as expected).
Another optimization we tried was adding class weights to overcome the input bias of classes 1 and 5, we used class_weight helpers from sklearn 
but obtained better results with custom weights. We think the reason is behind the conformation of the input images: by simply looking
at some of them we observed that some images were less characterizing that others (e.g. brightness, focus, zoom).  
To push our network further, we've introduced a learning rate schedule to increase the training efficiency in later epochs,
used batch normalization in between the CNN and increased both filters and dense layers units
up to 512 filters on the last convolution layer and 600 units in the dense layer.
As our final design for the custom CNN reached 0.7762 on codalab, we decide to move to transfer learning.