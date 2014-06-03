function [x_new,y_new,success]=TreaCatch(x,y,x_target,y_target,xcop,ycop)
% TreaCatch function is an improvement of Catch function. this function can be 
% applied for the situation that the thief catch treasure and run away from 
% the cops at the same time which means the thief have a target(treasure or
% escape route) but he should also try to avoid catching by the cops. the
% strategy of the thief movement remains the same to the Catch function,
% but it was added a loop that check whether next step has cops, if there 
% is a cop there the thief will go another way.

%P.S. strategy for Catch function
% Catch.m function is that finding the maximum coordinate firstly and
% then determine a direction which could shorten this way. However, there
% are two directions that cops can be chosen to lower the distance to the 
% thief, so we choose the direction with a higher range. When the distance
% to two directions are the same, the direction for the next step step is
% chosen random. REPEAT


% difference to the thief in x y direction respectively
dist_x=x-x_target;
dist_y=y-y_target;
% the distance in x direction is greater than y's., thief move along x direction
if abs(dist_x)>abs(dist_y)
        success=0;
        % move to west
        if dist_x>0
            % there is a cop in vertical direction
            if (isempty(find(xcop==x-1,1))==0&&isempty(find(ycop(find(xcop==x-1,1))==y,1))==0)
                % to north
                if dist_y>0
                    % a cop at north
                    if isempty(find(ycop==y-1,1))==0
                        y=y;
                    % no cop on the north direction(cop at the south)
                    else
                        % move to south
                        y=y-1;
                    end
                % to south
                else
                     % a cop at south
                    if isempty(find(ycop==y+1,1))==0
                        y=y;
                    % no cop on the south direction (cop at the north)
                    else
                        %move to north
                        y=y+1;
                    end
                end
            % there is no cop in vertical direction
            else
                %move to west
                x=x-1; 
            end
        % move to east
        else
            % there is a cop in vertical direction
            if (isempty(find(xcop==x+1,1))==0&&isempty(find(ycop(find(xcop==x+1,1))==y,1))==0)
                % to north
                if dist_y>0
                    % a cop at north 
                    if isempty(find(ycop==y+1,1))==0
                        % not move
                        y=y;
                    % no cop on the north direction(cop at the south)
                    else
                        % move to north
                        y=y+1;
                    end
                else
                    % a cop at south 
                    if isempty(find(ycop==y-1,1))==0
                        % not move
                        y=y;
                    % no cop on the south direction(cop at the north)
                    else
                        % move to south
                        y=y-1;
                    end
                end
            % there is no cop in vertical direction
            else
                 %move to east
                x=x+1;
            end
        end
% the distance in y direction is greater than x's., thief move along y direction
% similar to the strategy above, chang the vertical to the horizontal,
% north to east, south to west.
elseif abs(dist_y)>abs(dist_x)
        success=0;
        if dist_y>0
            if (isempty(find(ycop==y-1,1))==0&&isempty(find(xcop(find(ycop==y-1,1))==x,1))==0)
                if dist_x>0
                    if isempty(find(ycop==x-1,1))==0
                        x=x;
                    else
                        x=x-1;
                    end
                else
                    if isempty(find(ycop==x+1,1))==0
                        x=x;
                    else
                        x=x+1;
                    end
                end
            else
                y=y-1;
            end
        else
            if (isempty(find(ycop==y+1,1))==0&&isempty(find(xcop(find(ycop==y+1,1))==x,1))==0)
                if dist_x>0
                    if isempty(find(ycop==x-1,1))==0
                        x=x;
                    else
                        x=x-1;
                    end                    
                else
                    if isempty(find(ycop==x+1,1))==0
                        x=x;
                    else
                        x=x+1;
                    end
                end
            else
                y=y+1;
            end
        end
else
    if (dist_x==0) && (dist_y==0)
        success=1;
    else
        success=0;
        if dist_x>0
            if (isempty(find(xcop==x-1,1))==0&&isempty(find(ycop(find(xcop==x-1,1))==y,1))==0)
                if dist_y>0
                    if isempty(find(ycop==y-1,1))==0
                        y=y;
                    else
                        y=y-1;
                    end
                else
                    if isempty(find(ycop==y+1,1))==0
                        y=y;
                    else
                        y=y+1;
                    end
                end
            else
                x=x-1; 
            end
        else
            if (isempty(find(xcop==x+1,1))==0&&isempty(find(ycop(find(xcop==x+1,1))==y,1))==0)
                if dist_y>0
                    if isempty(find(ycop==y+1,1))==0
                        y=y;
                    else
                        y=y+1;
                    end
                else
                    if isempty(find(ycop==y-1,1))==0
                        y=y;
                    else
                        y=y-1;
                    end
                end
            else
                x=x+1;
            end
        end
    end
end
x_new=x;
y_new=y;
end