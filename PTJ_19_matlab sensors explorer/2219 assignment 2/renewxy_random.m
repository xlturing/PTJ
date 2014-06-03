function [new_x, new_y] = renewxy_random(origin_x,origin_y,L)

    Ran_orient=random('unid',5);    %Randomly move to four direction and stop there
    switch Ran_orient
        case 1
            origin_x=origin_x-1;
        case 2
            origin_y=origin_y+1;
        case 3
            origin_x=origin_x+1;
        case 4
            origin_y=origin_y-1;
        case 5
    end
          
    while abs(origin_x)>=round(L/2) || abs(origin_y)>=round(L/2)  %Clip with the wall.
        if abs(origin_x)>=round(L/2) && origin_x<0 
           meet_westwall;
        elseif abs(origin_x)>=round(L/2) && origin_x>0
           meet_eastwall;
        end
            
        if abs(origin_y)>=round(L/2) && origin_y<0 
           meet_southwall;
        elseif abs(origin_y)>=round(L/2) && origin_y>0
           meet_northwall;
        end
    end
       
    function meet_westwall     %Meet west wall.    
        if abs(origin_y) < round(L/2)   %Just meet the wall, there are three choices.
            Ran = randi([1,3],1,1);
        elseif origin_y >= round(L/2)   %Meet the top corner, there are two choices.
            Ran = randi([1,2],1,1);
        elseif origin_y <= -round(L/2)  %Meet the bottom corner, there are two choices.
            Ran = randi([2,3],1,1);
        end
        
        switch Ran
                case 3  %Move done alone the wall
                    origin_x = -round(L/2);
                    origin_y = origin_y + 1;
                case 2  %Move back to the room
                    origin_x = -round(L/2) + 1;
                case 1  %Move up alone the wall
                    origin_x = -round(L/2);
                    origin_y = origin_y - 1;    
        end
    end
   
    function meet_eastwall    %Meet east wall, same to above.
        if abs(origin_y) < round(L/2)  
            Ran = randi([1,3],1,1);
        elseif origin_y >= round(L/2)
            Ran = randi([1,2],1,1);
        elseif origin_y <= -round(L/2)
            Ran = randi([2,3],1,1);
        end
        
        switch Ran
                case 3
                    origin_x = round(L/2);
                    origin_y = origin_y + 1;
                case 2
                    origin_x = round(L/2) - 1;
                case 1
                    origin_x = round(L/2);
                    origin_y = origin_y - 1;     
        end
    
    end
    
    function meet_southwall    %Meet south wall, same to above.
        if abs(origin_x) < round(L/2)  
            Ran = randi([1,3],1,1);
        elseif origin_x >= round(L/2)
            Ran = randi([1,2],1,1);
        elseif origin_x <= -round(L/2)
            Ran = randi([2,3],1,1);
        end
        
        switch Ran
                case 3
                    origin_y = -round(L/2);
                    origin_x = origin_x + 1;
                case 2
                    origin_y = -round(L/2) + 1;
                case 1
                    origin_y = -round(L/2);
                    origin_x = origin_x - 1;
        end
    end

    function meet_northwall    %Meet north wall, same to above
        if abs(origin_x) < round(L/2)  
            Ran = randi([1,3],1,1);
        elseif origin_x >= round(L/2)
            Ran = randi([1,2],1,1);
        elseif origin_x <= -round(L/2)
            Ran = randi([2,3],1,1);
        end
        
        switch Ran
                case 3
                    origin_y = round(L/2);
                    origin_x = origin_x + 1;
                case 2
                    origin_y = round(L/2) - 1;
                case 1
                    origin_y = round(L/2);
                    origin_x = origin_x - 1;
        end
    end

              
    new_x=origin_x; %Give out the input.
    new_y=origin_y;

end

