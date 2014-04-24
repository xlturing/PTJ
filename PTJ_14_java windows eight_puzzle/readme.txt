MyButton.java:
This class extends from JButton which handle the box move issue. Include info£ºbox target location£¨final_x,final_y), box current location (now_x,now_y) step number between box and target location (leamove).

MyManager.java:
This class is the algorithm implementation

SimplePane.java:
Windows handle box move problem

Windows.java:
The main method in there
and include the window implementation and call algorithm


Algorithm:
1.Breadth First Search
weakness: search the result without any rule, so the efficiency is very low.

2.Heuristic Search
weakness: this search improve the search efficiency, but need more calculated amount.