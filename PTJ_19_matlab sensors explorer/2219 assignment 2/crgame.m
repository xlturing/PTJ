% ENGN2219 Assignment 2: Thief and Cops Game Simulation
 
% Main loop starts at:Line 186.
 
% Author Details:
 
% 1.Name: Zesheng Cheng
% Student ID: U5072689
 
% 2.Name: Di Zhu 
% Student ID: U4983805
 
% Appropriate time: 42 hours totally.
 
% Description of the problem:
 
% A thief tries to steal a treasure that is located in a room and a number
% of cops try and capture him before he steals the treasure.
% Neither the thief nor the cops know exactly where the treasure is
% located. The treasure has been cloaked, but the thief has an
% anti-cloaking device that allows him to see the treasure, if he is within
% a certain distance of the treasure.   
% There are several cops.  Each cop or thief has a sensor range, a circle
% of a specified radius, within which he can sense the presence of another
% person (thief or cop) or of the wall. If a cop knows the position of the
% thief, then he can't pass on this information to another cop, unless he
% is within the sensor range of that cop. That is, they need to be within
% each other's sensor range.     
% The room is rectangular. The centre of the room has the co-ordinates
% (0,0). There is a circle with origin at the centre of the room, the
% circumference of which provides random points for people to enter the
% room. The position on the circumference is determined by generating a
% uniform random angle between 0 and 2*pi. People enter the room at random
% times determined by generating a uniform random integer in the range
% [0..max_entry_time]. This time is the elapsed time with respect to the
% entry of the previous person. Time is counted in the form of discrete
% time steps. 
% A thief enters the room at a random time and a random point on the
% circumference of the entry circle, as specified above, after the start of
% the game. Once the thief has entered the room, a given number of cops
% enter in the same way, one after the other, at random times and points,
% again as specified above. The room is divided into a square grid, each
% cell of which is a square with sides of size 1cm. A cop/thief can move
% one grid point at a time, in one of four directions (north, east, south,
% or west). They can choose not to move as well. Once they reach a wall,
% they can only move along the wall, or back into the room. 
% The thief moves randomly (the default strategy), until he locates the
% treasure. To do so, the treasure has to be in the range of his
% anti-cloaking device. He steals the treasure if he moves to the same
% position as the treasure. The thief is captured if a cop moves into the
% same position as the thief. In addition, the thief knows of an escape
% route (a position on the grid), which the cops know nothing about. Once
% the thief grabs the treasure he moves as quickly as he can to the escape
% route. He will not try to escape before he grabs the treasure. He escapes
% if he lands on the escape route co-ordinates. The time limit for the
% game, that is, the maximum number of time steps for which the game can
% run, is specified. The game ends when the thief steals the treasure  and
% escapes via the escape route or is captured or the time limit is
% exceeded.  
 
% Approach to solving the question: 
 
% We divide the simulation into three part:
 
% A: Read and check the file.
% B: Read and check the parameter:
 
% Here we think if the parameter is wrong, the program should stop
% immediately, So I use return to stop the program.
 
% Also, there should be another choice for good strategy, so here should
% also be a loop for asking user to choose the strategy and check if it is
% valid.
 
% C: Main Simulation.
 
% Firstly, it should check if the condition of finish the game has achieved,
% If thief or cops win the game. Print out the result.
 
% Else, start the simulation from step one. For step one, create a figure
% and draw all the stable elements on it: boundary, entry circle, step
% clock, label, title, origin position of the treasure and escape point.
 
% For the other step, simulation the thief movement in four condition below:
 
% Condition A: There is cops in the sensor range of thief, thief evade from
% the cops. (Evade Function)
 
% Condition B: There is no cops in the sensor range of the thief and the
% thief does not know the position of the treasure. Good strategy should be
% achieved here. (Pursue, Search and RandomXY Function)
 
% Condition C: If the thief know the position of the treasure. (Pursue
% Function)
 
% Condition D: If the thief has got the treasure and been going to escape
% point. (Pursue Function)
 
% For cops, their is two condition:
 
% Condition A: The thief is in the sensor range of a cop or a cop is in the
% sensor range of other cop which got the thief in his sensor range.
% (Pursue Function)
 
% Condition B: No thief in the sensor range, just walk randomly. (RandomXY
% Function)
 
% If game finish with draw condition, print out the result.
 
% Expression of the functions (Also see commence in the function files):
 
% Evade (Appears at line)
 
% Use to escape from the cops to opposite direction. For example, if the
% cops appears at Northeast, thief will evade to either south or west. If
% he clip with the wall while evading, he can change his decision and find
% another way to escape in the same step.
 
% Pursue (Appears at line)
 
%Use to pursue something in any condition, such as the thief and treasure,
%thief and escape point and cops and thief. It is also use to achieve
%Search function and loop. The route should be randomly with a shortest 
%distance. 
 
% RandomXY (Appears at line)
 
% Walk randomly and check if the character has meet the wall in each
% condition. It is the foundational method for each character (Thief and
% Cops).
 
% Search (Appears at line, just available for food strategy).
 
% A simple direction use to change and search aim point and change direction.
 
% Description of the good strategy.
 
% This strategy was finished on Thursday. There may still some bugs,
% however, we try our best to fix most of them and for the recent 20 times, 
% no bugs happened. The strange for thief to achieve is shown as graph
% below:
 
%         _______________________________
%         |       ___     ___     ___    |
%         |  |1  |4  |5  |   |   |   |0  |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |   |   |   |   |   |   |   |
%         |  |2__|3  |6__|   |___|   |0  |
%         |______________________________|
 
% Description of the good strategy, 
 
% Thief will try to move from entry point to one of the zero points. then
% try to move to point 1 and then point 2 and so on. It will search while
% he working. 
 
% Feedback:
 
% It is truly an interesting assignment which we spent a lot of time on.
% The assignment details are clearly enough for us to understand what we
% need to do (at least more clearly than the assignment one). However,
% there still are some difficulties. The first thing is that the good
% strategy part, which we spent a lot of time on it. We think it could be
% the bonus part rather than in the normal marked part. Or the mark for the
% part should be higher rather than just only 10%. The second thing is that
% we think we could have much easier assignments and maybe them can be
% divided into to 3-5 assignments, which is sound more excited. Also, since
% we can get a sample solution of each assignment, it is really good for us
% to learn from it. The third thing is we think we could have an
% opportunity to present the work load. Because It is very difficult for
% group to divide the assignment into two equal part.  
 
 
% Main program start.
 
 
 
close all;
clear all;
clc;
 
% PartI: Read and check if the input file is valid.
 
fileID = -1;
while fileID == -1
    file_name = input('Input File Name:','s');  %If valid, keep the file's name.
    fileID = fopen(file_name);
    
    if fileID == -1
        fprintf('Wrong file name. Please input a valid file Name.\n');  %If not, ask a valid file without stopping the program.
    end
end
 
 
% PartII: Load and check parameters and strategy.
 
%Several parameter to control the input in while loop:
 
data_correct=0; %Use to check the data is correct.
arg_number=0;   %Use to count the number of information lines (Beside the '#' lines).
tline=0;        %Use the ignore the '#'.
 
%Read the data
 
while data_correct==0  
    tline = fgetl(fileID);
    
    if tline(1) == '#'    %Use to ignore the '#' and then count the number of the line.
        arg_number = arg_number+1;
        tline = fgetl(fileID);
        
        switch arg_number
            
            case 1  %Read the size of the room: L.(L should be a positive number)
                L = cell2mat(textscan(tline,'%.2f'));
                if L <= 0 || L > 999999999  %Parameter check, if not valid, end the program.
                    fprintf('Please check the length of the room \n');  %Ask user to check the parameter.
                    return;
                end
                
            case 2  %Read the size of the entry circle: R.(R should be a positive number but smaller than L)    
                R = cell2mat(textscan(tline,'%.2f')); 
                if R > L/2 || R < 0 %Parameter check, if not valid, end the program.
                    fprintf ('Please check the length of the room and the radium of the entry circle \n');    %Ask user to check the parameter.    
                    return;
                end
                
            case 3 %Read the position of the treasure: treasure_x and treasure_y.(Should ensure that the treasure is in the room)
               temp = cell2mat(textscan(tline,'%.2f',2));
               treasure_x = temp(1,1);
               treasure_y = temp(2,1);
               if abs(treasure_x) > L/2 || abs(treasure_y) > L/2    %Parameter check, if not valid, end the program.
                    fprintf ('Please check the position of the treasure \n');    %Ask user to check the parameter.
                    return;
               end
               
            case 4  %Read the anti-cloak radius of the thief: anti_cloak_r. (It should be a positive number smaller than the size of the room).
                anti_cloak_r = cell2mat(textscan(tline,'%d'));
                if anti_cloak_r < 0 || anti_cloak_r > 0.5*sqrt(2)*L*100 %Parameter check, if not valid, end the program.
                    fprintf ('Please check the anti-cloak range of the thief \n');  %Ask user to check the parameter.
                    return;
                end
                
            case 5  %Read the position of the escape point: escape_x and escape_y. (Should ensure that the escape point is in the room)
                temp = cell2mat(textscan(tline,'%.2f',2));
                escape_x = temp(1,1);
                escape_y = temp(2,1);
                if abs(escape_x) > L/2 || abs(escape_y) > L/2   %Parameter check, if not valid, end the program.
                    fprintf ('Please check the position of the escape point \n');   %Ask user to check the parameter.
                    return;
                end
                
            case 6  %Read the total time of the game: Total Time. (Total Time should be a positive integer)
                TotalTime = cell2mat(textscan(tline,'%d'));   
                if rem(TotalTime,1) ~= 0 || TotalTime < 0 || TotalTime > 9999999999   %Parameter check, if not valid, end the program.
                    fprintf('Please check the value of the total step \n'); %Ask user to check the parameter.
                    return;
                end
                
            case 7  %Read the entry time of the character:max_entry_time. (It should be a positive integer)
                max_entry_time = cell2mat(textscan(tline,'%d'));
                if rem(max_entry_time,1) ~= 0 || max_entry_time < 0 %Parameter check, if not valid, end the program. 
                    fprintf ('Please check the value of the max entry time \n');    %Ask user to check the parameter.
                    return;
                end
                
            case 8  %Read the sensor range of the thief: thief_sensor_range. (It should be a positive number smaller than the size of the room)
                thief_sensor_range = cell2mat(textscan(tline,'%d'));
                if thief_sensor_range < 0 || thief_sensor_range > 0.5*sqrt(2)*L*100  %Parameter check, if not valid, end the program.
                    fprintf ('Please check the sensor rang of the thief \n')    %Ask user to check the parameter.
                    return;
                end
                
            case 9  %Read the number of the parameter and the sensor range of each of them. (k should be a positive integer)
                k = cell2mat(textscan(tline,'%d'));   
                cop_sensor_range = zeros(1,k,'int8'); %Sensor range will be checked out of the while loop
                if rem(k,1) ~= 0 || k < 0 || max_entry_time * k > TotalTime    %k*max_entry_time should less than Total Time to ensure that all the cop can enter the game. 
                    fprintf ('Please check the number of cops /n'); %Ask user to check the parameter.
                    return;
                end
       end
       
       if arg_number > 9 %If the number of information line bigger than nine, all of the information should be the sensor range of each cop.
           cop_sensor_range(1,arg_number-9) = cell2mat(textscan(tline,'%d')); %Read the sensor range of each cop
       end
    end
    
    %If all of the parameter are read and valid, keep the parameter and end the loop.
    
    if tline == -1 %The line without '#'.
       data_correct = 1;
    end
end
 
% Check the sensor range of the cop. (Sensor range should be a positive number and less than the size of the room)
 
for i = 1:k
    if cop_sensor_range(1,i) < 0 || cop_sensor_range(1,i) > 0.5*sqrt(2)*L*100
        fprintf('Please check the sensor range of the cop %d \n', i); 
        return;
    end
end
 
Strategy_try = -1; %Ask user to choose the strategy for the game: 1 for random, 2 for clever thief.
while Strategy_try == -1
    try Strategy = input('\nPlease input choose the strategy for the game \n (1 for random, 2 for clever thief):');
        Strategy_try = 0;
        if Strategy ~= 1 && Strategy ~= 2   %If the input is valid.
            fprintf('\nPlease choose a valid strategy (1 for random, 2 for clever thief): \n');
            Strategy_try = -1;
        end
    catch
        fprintf('\nPlease choose a valid strategy (1 for random, 2 for clever thief): \n');
        Strategy_try = 0;
    end
end
 
fprintf('\n Congratulations! All the parameter is valid, game start now ! \n');
 
 
%Part III: Main simulation of the game.
 
%Convert m into cm. (For L, R, treasure position and escape position)
 
L = L * 100;
R = R * 100;
treasure_x = treasure_x*100;
treasure_y = treasure_y*100;
escape_x = escape_x*100;
escape_y = escape_y*100;
 
%Choose the entry time and position for the thief and cops randomly.
 
reset(RandStream.getGlobalStream,sum(fix(clock)))                   %Reset the global random stream with current date and time.
thief_entry_time = round(rand(1)*single(max_entry_time)) + 1;       %Entry time for thief.
cop_entry_time = round(rand(1,k)*single(max_entry_time)) + 1;       %Entry time for each cop.
thief_entry_point = rand(1)*2*pi;                                   %Entry point for thief.
cop_entry_point = rand(1,k)*2*pi;                                   %Entry time for cop.
 
%Several parameter to control the simulation
 
thief_coor = zeros(1,2);        %Store the position of the thief.
cop_coor = zeros(1,k,2);        %Store the position of each cop.
treasure_get_flag = 0;          %Store if the treasure has been got by thief.
cop_entry_count = 0;            %Store the number of the entered cops.
Game_end_flag = 0;              %Store if the game finish condition has achieved.
treasure_known_flag = 0;        %Store if the thief know the position of the flag.
 
%Parameter for good strategy only.
 
if Strategy == 2
    right_wall_met=zeros(1,3);  %Three number for Northeast corner met, Southeast corner met, East wall met.
end
 
for step=1:TotalTime
    pause(0.01);
    
    %Game finish condition A: Thief stole the treasure and escape from the room successfully.
    
    if round(thief_coor(1,1))==round(escape_x) && round(thief_coor(1,2))==round(escape_y) && treasure_get_flag~=0
        fprintf('Thief wins at step %d! \n',step)
        Game_end_flag=1;
    end
    
    %Game finish condition B: Thief is caught buy any of the cop.
    
    for cop_i=1:k
        if round(cop_coor(1,cop_i,1))==round(thief_coor(1,1)) && round(cop_coor(1,cop_i,2))==round(thief_coor(1,2)) && thief_entry_time==0 && cop_entry_time(1,cop_i)==0
            fprintf('Cops win at step %d! \n',step);
            Game_end_flag=1;
            break;
        end
    end
    
    if Game_end_flag~=0 %If the condition has achieved, finish the game immediately.
       break;
    end
   
    thief_moved_flag=0;
    if step==1  %For first step, plot all the needed parameter.
        game_fig=figure;    %Create a figure.
        
        %Loop for plot the boundary.
        
        x_length=-L/2:0.1:L/2;  %Create x.
        [~, draw_temp]=size(x_length);
        y_temp_north=zeros(1,draw_temp);
        y_temp_south=zeros(1,draw_temp);
        
        for boundary_size=1:draw_temp    %Create y correspond to x
            y_temp_north(1,boundary_size)=L/2;
            y_temp_south(1,boundary_size)=-L/2;
        end
        
        hold all
        plot(x_length,y_temp_north,'LineWidth',3,'color','k');  %North Wall
        plot(x_length,y_temp_south,'linewidth',3,'color','k');  %South Wall
        plot(y_temp_south,x_length,'linewidth',3,'color','k');  %West Wall
        plot(y_temp_north,x_length,'linewidth',3,'color','k');  %East Wall  
        axis([-L*1.1/2,L*1.1/2,-L*1.1/2,L*1.1/2]);              %Axis
        grid on                                                 %Grid
        axis square                                             %Make the grid as a square.
        title('Thief and Cops')                                 %Title 
        xlabel('cm');
        ylabel('cm');
        treasure_text=text(treasure_x,treasure_y,'\bullet','fontsize',10,'color','g');  %Point for the treasure.
        text(escape_x,escape_y,'\bullet','fontsize',15,'color','y');    %Point for the escape point.
        theta=0:0.001:2*pi; %Draw the entry circle.
        plot(R*cos(theta),R*sin(theta),'linewidth',2,'color','c');
        cop_dot=plot(cos(theta)+L,sin(theta)+L,':','color','b');    %Legend to the cop.
        escape_dot=plot(cos(theta)+L,sin(theta)+L,':','color','y'); %Legend to escape point.
        treasure_dot=plot(cos(theta)+L,sin(theta)+L,':','color','g');   %Legent to the treasure.
        thief_dot=plot(cos(theta)+L,sin(theta)+L,':','color','r');  %Legend to the thief.
        legend_h=legend('North Wall',' South Wall','East Wall','West Wall','Entering Circle','Cops','Escape','Treasure','Thief','Location','NorthEastOutside');    %Plot the legend.
        
        if thief_entry_time==1  %Ensure that the thief enter the room at step 0.
            thief_coor=[round(R*cos(thief_entry_point)),round(R*sin(thief_entry_point))];
            thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color','r');   %First point for the thief.
            thief_entry_time=0; %Flag for thief entry.
        end
        
        %Step clock on the figure.
        
        st=sprintf('Step = %d',step);
        time_text=text(-L*1.15/2,L*1.19/2,st,'FontSize',10);   
        orient=randi(2,1)-1;    %random direction for thief searching 0:North direction, 1:South direction.
        side_move=0;            %If the thief has meet the wall.
    
        
    %Four condition for thief's movement.
    
    else    %For other step, main simulation.
        st=sprintf('Step = %d',step);
        delete(time_text);
        time_text=text(-L*1.15/2,L*1.19/2,st,'FontSize',10);
        
        %When the thief has not appeared.
        
        if thief_entry_time~=0
            thief_coor=[round(R*cos(thief_entry_point)),round(R*sin(thief_entry_point))];
            thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color','r');
            thief_entry_time=0; %Flag for thief entry.
            
        %After the thief enter the room.
        
        else
            %Condition A: There is cops in the sensor range of thief, thief evade from the cops.
            
            for cop_i=1:k %For each cop.
                
                %Give the condition.
                
                if sqrt((cop_coor(1,cop_i,1)-thief_coor(1,1))^2+(cop_coor(1,cop_i,2)-thief_coor(1,2))^2) <= thief_sensor_range && cop_entry_time(1,cop_i)==0
                    
                    if treasure_get_flag==0 %If thief has not got the treasure, plot a red filled point.
                        delete(thief_text);
                        [thief_coor(1,1),thief_coor(1,2)] = evade(thief_coor(1,1),thief_coor(1,2),cop_coor(1,cop_i,1),cop_coor(1,cop_i,2),L,thief_sensor_range,0);  %Evade function.
                        thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color','r');
                        thief_moved_flag=1; %Flag for the thief has moved.
                        break;
                    else    %If thief has got the treasure, plot a brown filled circle.    
                        delete(thief_text);
                        [thief_coor(1,1),thief_coor(1,2)] = evade(thief_coor(1,1),thief_coor(1,2),cop_coor(1,cop_i,1),cop_coor(1,cop_i,2),L,thief_sensor_range,0);  %Evade function.
                        thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color',[0.82 0.7 0.55]);
                        thief_moved_flag=1; %Flag for the thief has moved.
                        break;
                    end
                end
            end
            
            %Flag for if the thief has known the position of the treasure.
            
            if treasure_known_flag==0 && sqrt((thief_coor(1,1)-treasure_x)^2+(thief_coor(1,2)-treasure_y)^2) <= anti_cloak_r
               treasure_known_flag=1;
            end
            
            %Condition B: There is no cops in the sensor range of the thief and the thief does not know the position of the treasure.
            
            if treasure_known_flag==0 && thief_moved_flag==0
                delete(thief_text);
                
                if Strategy == 1    %For random strategy.
                    
                    [thief_coor(1,1),thief_coor(1,2)]=renewxy_random(thief_coor(1,1),thief_coor(1,2),L);    %Thief move randomly.
                    thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color','r');
                    thief_moved_flag = 1;   %For thief has moved.
                          
                elseif Strategy == 2    %For clever thief.
                
                    if (L/2-thief_coor(1,1))<= anti_cloak_r && thief_coor(1,1)>0 && ~(right_wall_met(1,1) && right_wall_met(1,2)) %East wall is in thief's anti-cloak range.
                        
                        %For if the thief has meet the corner.
 
                        if thief_coor(1,2)>0 && (L/2-thief_coor(1,2))<= anti_cloak_r %Northeast corner is in the anti-cloak range.
                            right_wall_met(1,1)=1;  %First number: the Northeast corner has be searched.
                            side_move=0;    %Does not need to turn right.
                        elseif thief_coor(1,2)<0 && (thief_coor(1,2)+L/2)<= anti_cloak_r %Southeast corner is in the anti-cloak range.  
                            right_wall_met(1,2)=1;  %Second number: the Northeast corner has be searched.
                            side_move=0;    %Does not need to turn right.
                        end
 
                        if right_wall_met(1,1)==1 && right_wall_met(1,2)==1 %If the two corner has been searched, all the east wall is regards to be searched.
                            right_wall_met(1,3)=1;  %Third number: All the east wall has been searched.
                        end
                    end
                    
                    if right_wall_met(1,3)==1   %If the the east wall has been searched.
                        [thief_coor(1,1),thief_coor(1,2)]=pursue(thief_coor(1,1),thief_coor(1,2),round(-L/2+anti_cloak_r),round(L/2-anti_cloak_r)); %Pursue the Northwest corner.
                        
                        %If the Northwest corner has arrived.
                        
                        if thief_coor(1,1)==round(-L/2+anti_cloak_r) && thief_coor(1,2)==round(L/2-anti_cloak_r)
                            right_wall_met=zeros(1,3);  %Search from west to east wall.
                            side_move=0;    %Does not need to turn.
                            orient=1;   %Walk to South until meet the wall.
                        end
 
                    elseif side_move~=0 %If need to turn and right_wall_met(1,3)==0 here.
                        [thief_coor(1,1),thief_coor(1,2)]=pursue(thief_coor(1,1),thief_coor(1,2),target_x,target_y);    %Pursue a position on the right which distance is twice of the anti-cloak range.
                        if thief_coor(1,1)==target_x && thief_coor(1,2)==target_y    %If arrived the point, no need to turn right.
                           side_move=0;
                        end
                    elseif side_move==0 %If does not need to turn and right_wall_met(1,3)==0 here.
                        orient_old=orient;  %Record the old direction.
                        [thief_coor(1,1),thief_coor(1,2),orient]=search(thief_coor(1,1),thief_coor(1,2),L,orient);  %Search function alone this direction.
                        
                        if orient~=orient_old   %While meet the wall, change to opposite direction, if not, keep the direction.
                            side_move=1;    %While meet the wall, need to turn right.
                            target_x=thief_coor(1,1)+2*anti_cloak_r-2;  %Change a new aim position.
 
                            if target_x>L/2 %Keep the aim in the room.
                               target_x=round(L/2-1);
                            end
 
                            if thief_coor(1,2)>0    %If meet the north wall, the aim change to a point at north.
                                target_y=thief_coor(1,2)-anti_cloak_r;
                            elseif thief_coor(1,2)<0    %If meet the south wall, the aim change to a point at south.
                                target_y=thief_coor(1,2)+anti_cloak_r;
                            else    %Else, keep walking.
                                target_y=thief_coor(1,2);
                            end
                        end
                    end
                
                thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color','r');   %Plot a filled circle for the thief.
                end
            
            %Condition C:  If the thief know the position of the treasure.   
                
            elseif treasure_known_flag~=0 && treasure_get_flag==0 && thief_moved_flag==0
                
                if round(thief_coor(1,1))==round(treasure_x) && round(thief_coor(1,2))==round(treasure_y)   %If the treasure has be stolen.
                    delete(treasure_text);  %Delete the position of the treasure.
                    treasure_get_flag=1;
                    delete(thief_text);
                    thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color',[0.82 0.7 0.55]);   %Plot the a new filled brown circle of the thief.
                    delete(legend_h);
                    delete(thief_dot);  %Delete the thief's legend, change to brown one.
                    delete(treasure_dot);   %Delete the treasure legend
                    thief_dot=plot(cos(theta)+L,sin(theta)+L,':','color',[0.82 0.7 0.55]);
                    legend_h=legend('North Wall',' South Wall','East Wall','West Wall','Entering Circle','Cops','Escape','Thief','Location','NorthEastOutside');
                    
                else   %If the treasure has not been stolen.
                    [thief_coor(1,1),thief_coor(1,2)]=pursue(thief_coor(1,1),thief_coor(1,2),treasure_x,treasure_y);    %Pursue the treasure.
                    delete(thief_text);
                    thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color','r');    %Plot the a new filled red circle of the thief.
                
                end
                
            %Condition D: If the thief has got the treasure and been going to escape point.
            
            elseif treasure_get_flag~=0 && thief_moved_flag==0
                [thief_coor(1,1),thief_coor(1,2)]=pursue(thief_coor(1,1),thief_coor(1,2),escape_x,escape_y);    %Pursue the escape point.
                delete(thief_text);
                thief_text=text(thief_coor(1,1),thief_coor(1,2),'\bullet','fontsize',10,'color',[0.82 0.7 0.55]);   %After got the treasure, change to filled brown circle.
            end
        end
    end
    
    %For cops move.
    
    cop_known_thief=zeros(1,k); %A array to store which police know the position of the thief.
    
    %For cops known the position of the thief, that number become 1.
    
    for cop_i=1:k
        if sqrt((cop_coor(1,cop_i,1)-thief_coor(1,1))^2+(cop_coor(1,cop_i,2)-thief_coor(1,2))^2)<= cop_sensor_range(cop_i)
            cop_known_thief(1,cop_i)=1;
        end
    end
    
    %For cops in other cops' sensor range, which known the position of the thief.
    
    for cop_i=1:k
        if cop_known_thief(1,cop_i)==1
            for cop_j=1:k
                if sqrt((cop_coor(1,cop_i,1)-cop_coor(1,cop_j,1))^2+(cop_coor(1,cop_i,2)-cop_coor(1,cop_j,2))^2)<= max(cop_sensor_range(cop_j),cop_sensor_range(cop_i))
                    cop_known_thief(1,cop_j)=1; %That number change to 1. 
                end
            end
        end
    end
    
    for cop_i = 1:k
        if cop_entry_time(1,cop_i)==0
            if cop_known_thief(1,cop_i)~=0  %If the cop know the position of the thief, pursue him.
                [cop_coor(1,cop_i,1),cop_coor(1,cop_i,2)]=pursue(cop_coor(1,cop_i,1),cop_coor(1,cop_i,2),thief_coor(1,1),thief_coor(1,2));
            else    %If not, just walk randomly.
                [cop_coor(1,cop_i,1),cop_coor(1,cop_i,2)]=renewxy_random(cop_coor(1,cop_i,1),cop_coor(1,cop_i,2),L);
            end
            delete(cop_text(1,cop_i));
            cop_text(1,cop_i)=text(cop_coor(1,cop_i,1),cop_coor(1,cop_i,2),'\bullet','fontsize',10,'color','b');    %Plot the new position of each cop.
        end
    end
    
    %Loop for cop enter the room.
    
    for cop_i=1:k
        
        if thief_entry_time==0 && cop_entry_time(1,cop_i)~=0 && cop_entry_count==cop_entry_time(1,cop_i)
            cop_entry_time(1,cop_i)=0;  %For this cop enter the room.
            cop_entry_count=0;  %Count the number for the cops.
            cop_coor(1,cop_i,1)=round(R*cos(cop_entry_point(1,cop_i))); %Round the cop to the nearest grid.
            cop_coor(1,cop_i,2)=round(R*sin(cop_entry_point(1,cop_i)));
            cop_text(1,cop_i)=text(cop_coor(1,cop_i,1),cop_coor(1,cop_i,2),'\bullet','fontsize',15,'color','b');    %Plot a filled circle for the cop.
            
        elseif thief_entry_time==0 && cop_entry_time(1,cop_i)~=0    %Turn to next cop.
            cop_entry_count=cop_entry_count+1;
            break;
        end
    end
end
 
%Finish game with draw, No finish condition has met.
 
if Game_end_flag == 0.
   disp('Game finishes! Draw! \n');
end
 
 

