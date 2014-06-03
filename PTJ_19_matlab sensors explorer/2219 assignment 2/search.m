function [new_x,new_y,new_orient ] = search(thief_x,thief_y,L,orient)

    if orient==0    %If orient equal to 0, thief will go north.    
       new_y=thief_y+1;
       new_x=thief_x;
    else   %Else, go south
       new_y=thief_y-1;
       new_x=thief_x;
    end

    new_orient=orient;  %Store the new orient.
    
    if abs(L/2-abs(new_y))<=1   %If achieved, change the boolean value of orient.
       new_orient=~orient;
    end
end

