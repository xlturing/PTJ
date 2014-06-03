function [x_new,y_new]=normal_Move(x,y,direction,room_siz)
% normail_run function can be applied to both thief and cop random move.
% When thief has not prospected the treasure or cops, he will move random.
% while as for the cops, they will move like this only at the moment that the
% thief does not appear in their sensor range or they do not have the thief
% position which was passed by other cops. Therefore, the input elements
% are the current position, the moving direction of the anaylised person and
% the room size. the strategy is that swithch the direction firstly and
% check whether the person has touch the wall. If the person has reach the
% wll, he will stay there, otherwise will move a step forward. REPEAT

k=length(x);
x_new=zeros(k,1);
y_new=zeros(k,1);
% the number of people being analyised
for i=1:k
    %determine the direction of the i step        
    switch direction(i) 
        %North    
        case 1          
            %the thief reached the north wall, not move
            if y(i)==room_siz/2      % reflection
               y(i)=y(i);
            %move 1cm to north direction
            else
                y(i)=y(i)+1;
            end
        %South
        case 2
            %the thief reached the south wall, not move
            if y(i)==-room_siz/2     % reflection
                y(i)=y(i);
            %move 1cm to south direction
            else
                y(i)=y(i)-1;
            end
        %East
        case 3
            %the thief reached the east wall, not move
            if x(i)==room_siz/2     % reflection
                x(i)=x(i);
            %move 1cm to east direction
            else
                x(i)=x(i)+1;
            end
        %West
        case 4
            %%the thief reached the west wall, not move
            if x(i)==-room_siz/2     % reflection
                x(i)=x(i);
            %move 1cm to wast direction
            else
                x(i)=x(i)-1;
            end
        %Not move 
        case 5
             x(i)=x(i);
             y(i)=y(i);
    end
         %new direction after i step  
         x_new(i)=x(i);
         y_new(i)=y(i);
end
end