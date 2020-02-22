# Rest-Service Kata

In this Kata we will try to implement a simple rest service which will implement a simple
calculator. The following functionality should be implemented:

* addition
* substraction
* division
* multiply

As type of numbers there should only be used integers. The division result should be rounded to
the lower integer number.

The rest service should use path parameters and the operation should be written as words. The result 
should be returned as normal text.

E.g: 

/addition/2/3    -> result: 5

/division/3/2    -> result: 1

/subtraction/3/2 -> result: 1