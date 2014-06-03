% ENGN2219 Assignment2 Writen by Wei Zhou U5073705 and Lu Chen U5249139

% The Ass2 can be divided into 3 parts, in terms of the input, the excute
% and the result presentation, In the following paragraphs these thing will
% be demonstrated clearly in terms of the Requirement, Strategy and 
% the way we approch the soultion.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%input: 
% 1. Requirements:In this program the input value was given in a file,which given
% by the user. in this stage we should read that file firstly
% and then extract the required parameters from the file. Moreover, the
% inputs need to be check. An error should be reported if there is
% insufficient number of input parameters or incorrect value given.
% 
% 2. Strategy
% Input all parameters from the file
%1. The indtoduction of parameters:
% -Size of side of square room (real number, to two decimal places), in m 
% -Radius of entry circle (real number, to two decimal places), m 
% -Position of treasure (x and y coords. Again real numbers, to two decimal
%  places), in m 
% -Range of anti-cloaking device, in cm 
% -Position of escape route (x and y coords. Real number, to two decimal
%  places), in m 
% -Time limit for simulation, integer 
% -Time limit for each person's entry (max time steps), integer 
% -Thief sensor range (radius. An integer), in cms 
% -Number of cops, k, an integer 
% -Cop 1 sensor range (radius. An integer), in cms 
% -Cop 2 sensor range (radius. An integer), in cms
%   ...
% -Cop k sensor range (radius. An integer), in cms 
%
%3. Soultion
% Important step
% -In this program, the the input value was given in a file ,which is 
%  given by the user. Therefore we decided to use a fuction named 
%  data_input that can be applied to the input stage. In addition, the
%  concept of the data_input wiil be demonstrated in that function file.
% The input overall procedural
% -Firstly, we should ask user the name of the file which contains the parameter  
%  value and apply the value into the program by calling the function named
%  data_input.
% -After that, we also need to generate the initinal data( time and 
%  position). To explain specifically, we generate the radius and mutiply 
%  it to the cos(angle) and sin(angle). Therefore the initinal x/y position 
%  for the thief and cops can be generated and saved into a vector.
% -Finally, we introduce three parameters which represent that the treasure
% has not been stole and thief/cops are not success.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Execute: 

% 1. Requirements: In this part, the reqiuement is quite complicated, to
% simplify we sum up and list it.
% SURGROUNDING:
% -The room is divided into a square grid(1cm*1cm)
% SENSOR RANGE:a circle of a specified radius, within which he can sense
% the presence of another person (thief or cop) or of the wall.
% THIEF
% -thief enters the room at a random time and a random point.
% -when treasure is in the range of his anti-cloaking device, he can move
%  to the same position and steal it.
% -thief know the escape route and he will move to there as quickly as
%  possible only when he grabs the treasure. 
% COP
% -cops enter one by one after the thief entering the room,at random times
%   and points.
% -cop knows the position of the thief, then he can't pass on this 
%   information to another cop, unless he is within the sensor range of 
%   that cop. That is, they need to be within each other's sensor range. 
% MOVING:
% -thief/cop can move one grid point or not move at a time. if move, one of 
%  four directions can be chosen
% -Once they reach a wall, they can only move along the wall, or back into
%  the room.
% TIME LIMINATION
% -the maximum number of time steps for which the game can run
% -The game ends when the thief steals the treasure and escapes  
%   or is captured or the time limit is exceeded.

%2. Strategy
% Before strat writing the program, we idenified the issue into two
% aspects, in terms of the time flow and the event. For the time flow, we
% devided it into three individual period, in terms of nobody in the 
% system, only thief, and cops appear. As for the events, we demonstrated
% event for thief and cops. Thief: find treasure, approch treasure, grab
% treasure and approch escape route. Cops: before appear, before whitnin
% the sensor range, catching the thief. Finally, we determine to categorise
% the time flow firstly, followed by the event classification.

%3. Solution
% Important function introduction
% -In this stage, the program will call 3 functions which represent 4
%  differnet types of movements. 
%  'normal_Move.m' can be applied to both thief and cop movement, before
%   prospect the treasutre or the thief.
%  'Catch.m' is used for cops' movement calculation when they find and
%   start catching the thief. 
%  'run_away.m'  is for the thief has not get the treasure when he is 
%   running away from the cops.
%  'TreaCatch' isfor the situation that the thief catch treasure and run away from 
%   the cops at the same time which means the thief have a target(treasure or
%   escape route) but he should also try to avoid catching by the cops.
%  These function will be explained speciffically in the every single .m 
%   file.
% The excute overall procedural
% -Firstly, calculate the thief's movement during the no-cop period by
%  applying the normal_Move function. Then, after cops enter one by one, the
%  problem becoming more complicated. If cops and thief prospect each other,
%  they will change their movement function to the Catch.m and run_away.m
%  respectively. If the thief prospect or after grab treasure, he will move 
%  to the treasure or escape route directly while eacape from the cops'
%  cature.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%Presentation

%1. Repuirement: In this part, we will animation and termination the
% program firstly, followed by the result presentation. Before really
% writing this stage, we also list the principles need to follow
% ANIMATION
% -Draw the position by using filled circles. to be more specically, red
%  for the thief, blue for the cops, green for the treasure and yellow for
%  escape slot, brown for thief with treasure(not red and yellow anymore)
% -Draw a thick lack line as the boundary of the room.
% -Erase the previous positions
% -Need to draw the positions at each time step.
% TERMINATION
% -The game ends when the thief steals the treasure or is captured or the
%  time limit is exceeded..
% RESULT PRESENTATION
% -the thief escape with treasure, thief win!
% -the thief is captured, cops win!
% -time limit is exceeded, draw
%% Termination and result
% -the thief escape with treasure, thief win!
% -the thief is captured, cops win!
% -time limit is exceeded, draw

%2. Strategy
% When reading the Ass2 introduction, we found drawnow function and pause
% command was introduced. However, we finally decided to use plot function.

%3. Soultion
% we clearify 2 catogratises in this part of the program. One is before and
% after the thief grab the tresure, the other is whether the cops appears
% or not. we determined clearified the treature firtly, followed by the
% whether there is a cop.
% if the thief escape with treasure, thief win! if the thief is captured, 
% cops win! if time limit is exceeded, draw
