function [new_x, new_y] = evade (thief_x,thief_y,cop_x,cop_y,L,thief_sensor_range,recursion_time)


recursion_time=recursion_time+1;    %Use to account the recursion time.

if recursion_time>5 %Set the maximum recursion time to 5.
    new_x = thief_x;    %Store the output x if recursion time used up.
    new_y = thief_y;    %Store the output y if recursion time used up.
    return;
end

a = randi([1,2]);   %To the random direction.

if a==1 %Four choices with move to y direction first.
    if thief_y>=cop_y
        thief_y=thief_y+1;
    elseif thief_y<cop_y
        thief_y=thief_y-1;
    elseif thief_x<=cop_x
        thief_x=thief_x-1;
    elseif thief_x>cop_x
        thief_x=thief_x+1;
    end
    
else    %Four choices with move to x direction first.
    if thief_x<=cop_x
        thief_x=thief_x-1;
    elseif thief_x>cop_x
        thief_x=thief_x+1;
    elseif thief_y>=cop_y
        thief_y=thief_y+1;
    elseif thief_y<cop_y
        thief_y=thief_y-1;
    end 
end

%Condition for meet the wall.

while abs(thief_x) >= abs(L/2) || abs(thief_y) >= abs(L/2)  
    
    if abs(thief_x)>=round(L/2) && thief_x<0 
        meet_westwall;
        
    elseif abs(thief_x)>=round(L/2) && thief_x>0
        meet_eastwall;
    end
    
    if abs(thief_y)>=round(L/2) && thief_y<0 
        meet_southwall;
        
    elseif abs(thief_y)>=round(L/2) && thief_y>0
        meet_northwall;
    end
end

new_x = thief_x;    %If meet the wall, then make another choice.
new_y = thief_y;



    function meet_westwall    %Meet west wall.
        Ran=randi(3,1);
        switch Ran    %Three choices, followed are the same.
                case 3
                    thief_x = -round(L/2);  %Walk along the wall.
                    thief_y = thief_y + 1;  
                case 2
                    thief_x = -round(L/2) + 1;  %Back to the room
                case 1
                    thief_x = -round(L/2);  %Walk along the wall.
                    thief_y = thief_y - 1;  
        end
        if sqrt((thief_x-cop_x)^2+(thief_y-cop_y)^2)<=thief_sensor_range    %If there still a thief in the sensor range, make another decision.
        [thief_x,thief_y]=evade(thief_x,thief_y,cop_x,cop_y,L,thief_sensor_range,recursion_time);   %Evade function again.
        end
    end
   
    function meet_eastwall    %Meet east wall,Same to above.
        Ran=randi(3,1);
        switch Ran
                case 3
                    thief_x = round(L/2);
                    thief_y = thief_y + 1;
                case 2
                    thief_x = round(L/2) - 1;
                case 1
                    thief_x = round(L/2);
                    thief_y = thief_y - 1;
                
        end
        if sqrt((thief_x-cop_x)^2+(thief_y-cop_y)^2)<=thief_sensor_range
        [thief_x,thief_y]=evade(thief_x,thief_y,cop_x,cop_y,L,thief_sensor_range,recursion_time);
        end
    end
    
    function meet_southwall    %Meet south wall, same agian.
        Ran=randi(3,1);
        switch Ran
                case 3
                    thief_y = -round(L/2);
                    thief_x = thief_x + 1;
                case 2
                    thief_y = -round(L/2) + 1;
                case 1
                    thief_y = -round(L/2);
                    thief_x = thief_x - 1;
        end
        if sqrt((thief_x-cop_x)^2+(thief_y-cop_y)^2)<=thief_sensor_range
        [thief_x,thief_y]=evade(thief_x,thief_y,cop_x,cop_y,L,thief_sensor_range,recursion_time);
        end
    end

    function meet_northwall    %Meet north wall, still the same.
        Ran=randi(3,1);
        switch Ran
                case 3
                    thief_y = round(L/2);
                    thief_x = thief_x + 1;
                case 2
                    thief_y = round(L/2) - 1;
                case 1
                    thief_y = round(L/2);
                    thief_x = thief_x - 1;
        end
        if sqrt((thief_x-cop_x)^2+(thief_y-cop_y)^2)<=thief_sensor_range
        [thief_x,thief_y]=evade(thief_x,thief_y,cop_x,cop_y,L,thief_sensor_range,recursion_time);
        end
    end   
end








