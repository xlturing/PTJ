function [x_new,y_new,success]=Catch(x,y,x_target,y_target)
% Catch function is used for cops' movement calculation when they find the thief 
% and start catching him. The initinal value is the current posotions of
% the cops(x y) and the thief position(x_target y_target) and the cops will 
% find the shortest route to approch the thief. The strategy applied to
% this function is that finding the maximum coordinate firstly and
% then determine a direction which could shorten this way. However, there
% are two directions that cops can be chosen to lower the distance to the 
% thief, so we choose the direction with a higher range. When the distance
% to two directions are the same, the direction for the next step step is
% chosen random. REPEAT


% difference to the thief in x y direction respectively
dist_x=x-x_target;
dist_y=y-y_target;
% the distance in x direction is greater than y's.
if abs(dist_x)>abs(dist_y)
    % move to west    
    if dist_x>0
                x=x-1; 
                success=0;
    % move to east    
    else
                x=x+1;
                success=0;
    end
% the distance in x direction is greater than y's.
elseif abs(dist_y)>abs(dist_x)
    % move to south    
    if dist_y>0
                y=y-1;
                success=0;
    % move to north    
    else
                y=y+1;
                success=0;
    end
% the distance in x direction equals to y's
else
    % catch the thief(has the same position to thief)
    if (dist_x==0) && (dist_y==0)
        success=1;
    % the distance in x direction equals to y's, and does not equals to 0
    else
         %chose the random direction  to the nest step
        ran=rand(1);
        % move along the x axial
        if ran>=0.5
            % move to west    
            if dist_x>0
                x=x-1;
                success=0;
            % move to east    
            else
                x=x+1;
                success=0;
            end
         % move along the y axial
        else
            % move to south    
            if dist_y>0
                y=y-1;
                success=0;
            % move to north    
            else
                y=y+1;
                success=0;
            end
        end
    end
end
% new direction
x_new=x;
y_new=y;
end
