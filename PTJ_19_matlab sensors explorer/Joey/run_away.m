function [x_new,y_new]=run_away(x,y,x_cop,y_cop,RmSize)
% run_away function is for thief has not grabbed the treasure and one or 
% more cops are in his sensor range. The input of this function are the 
% roomsize and the current position of the thief(x y) and the
% cops(x_cop,y_cop). The strategy is that there is a trial step for thief.
% The thief will go North, South, East and West for trial. Calculate the
% distance among all cops with thief's location in 4 directions. Choose the 
% maximum distance. And thief will go that direction for one step. REPEAT. 


%the thief protends to move a step to the 4 directions 
trial=zeros(4,2);
%find wheter there are cops remains the same position to the trial step. 
n1=length(find(x_cop==x+1)); % move to east
n2=length(find(x_cop==x-1)); % move to west
n3=length(find(y_cop==y+1)); % move to north
n4=length(find(y_cop==y-1)); % move to south

% thief does not reach the east edge of the room and no cop has the same
% position to this trial step (east).
if (x<RmSize && n1==0)
    %move a step to east
    trial(1,:)=[x+1,y];
else
    % not move
    trial(1,:)=[x,y];
end
% thief does not reach the west edge of the room and no cop has the same
% position to this trial step (west).
if (x>-RmSize && n2==0)
     %move a step to west
    trial(2,:)=[x-1,y];
else
    % not move
    trial(2,:)=[x,y];
end
% thief does not reach the north edge of the room and no cop has the same
% position to this trial step (north).
if (y<RmSize && n3==0)
     %move a step to north
    trial(3,:)=[x,y+1];
else
    % not move
    trial(3,:)=[x,y];
end
% thief does not reach the south edge of the room and no cop has the same
% position to this trial step (south).
if (y>-RmSize && n4==0)
     %move a step to south
    trial(4,:)=[x,y-1];
else
    % not move
    trial(4,:)=[x,y];
end

%calcute the diatance between the trial step and the cops
dist=zeros(1,4);
% 4 directions
for i=1:4
    % distance in 4 direction respectively
    dist(i)=sum(abs(trial(i,1)-x_cop).^2+(trial(i,2)-y_cop).^2);
end
%the maximum distance is lower than 8 and the thief is surrounded
if (max(dist)<=8 && length(find(dist==max(dist)))==4)
    % thethief does not move
    x_new=x;
    y_new=y;
%otherwise
else
    % the way thief can run away from the cops
    answ =find(dist==max(dist),1);
    x_new=trial(answ,1);
    y_new=trial(answ,2);
end
end