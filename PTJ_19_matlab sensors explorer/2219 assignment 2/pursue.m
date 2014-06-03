function [new_x, new_y] = pursue(catch_x,catch_y,aim_x,aim_y)
    a = randi([1,2]);
    
    %For pursue the aim, go x direction and y direction randomly
    
    if a == 1   %Move to x direction firstly.
        if catch_x>aim_x
           catch_x=catch_x-1;
        elseif catch_x<aim_x
            catch_x=catch_x+1;
        elseif catch_y>aim_y
            catch_y=catch_y-1;
        elseif catch_y<aim_y
            catch_y=catch_y+1;
        end
    
    else    %Move to y direction firstly
        if catch_y>aim_y
            catch_y=catch_y-1;
        elseif catch_y<aim_y
            catch_y=catch_y+1;    
        elseif catch_x>aim_x
            catch_x=catch_x-1;
        elseif catch_x<aim_x
            catch_x=catch_x+1;     
        end
    
    end
    
    new_x = catch_x;
    new_y = catch_y;
end

