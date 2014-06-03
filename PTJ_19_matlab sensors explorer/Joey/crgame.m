% This program is for  ENGN2219 Assignment2
% Writen by Wei Zhou U5073705 and Lu Chen U5249139
% 24/5/13
% we spend about 5 hours doing the perparation, 9 hours writing the
% program, 13 hours doing debug, 6 hours writing the comment and making
% some slightly changes. To sum up, this program took up over 33 hours to
% finish. Due to the number of the requirements and the compliceted movement 
% the ass2 is much more complicated compared to the first one. However, we
% still try our best approching the solution. There are some bugs that we 
% still can not solve properly such as how can thief run away when he is
% surroundede by the cops or reaches the corner. These issues might caused
% by the imperfect solution made or the situation analysied faultily or
% even the limitaion. In a word, we tried best to solve the problem.

%The Ass2 can be divided into 3 parts, in terms of the input, the excute
%and the result presentation. In the 'readme.m' file these 3 things
%will be introduced in detail.


close all;
clear;
clc;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Input step 1: Import parameter

% ask user the name of the file and calling the data_input.m function to
% import the parameters. 
% Filename=input('File name:','s'); 
 Filename='Para.txt';

% Use function to read data and check whether they are valid.
[RmSize,Radius,Trea,Anticlk,Escape,TimeTotal,TimeEnter,Thief,K,Cop]=data_input(Filename);
%cop=[100,100,100,100,100];
Anticlk=400;
% Input step 2: Generate initial data
% 1. random generate the time that thief and cops enter. Cops will only 
%    enter after thief appears. therefore, time_entry(1) is for the thief 
%    which maintains the smallest value.
time_entry=round(TimeEnter*rand(1,K+1));


% 2. random generate the position that thief and cops enter.  
%  random generate angles to enter the room, angle_entry(1) for thief
angle_entry=round(2*pi*rand(1,K+1)); 

%  random generate position of each person by mutiplying the Radius to the
%  cos(angle_entry) and sin(angle_entry). 
% Resultant position of each person
x=zeros(1,K+1);
y=zeros(1,K+1);

for i=1:1+K
    x(i)=round(Radius*cos(angle_entry(i)));
    y(i)=round(Radius*sin(angle_entry(i)));
end
% 3. Introduce parameters
%   the treasure has not been stole and thief/cops are not success.
Tsuccess=0;
Esuccess=0;
Csuccess=0;
x_thief=[];
y_thief=[];

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Execute step 1: establish timer
% Generate a vector resprsenting every single steps from the thief turn out
% which can be used for time flow control(when cops appear).
for t=1:K+1
    % sum the interval time up to get the moment people show up
    Time(t)=sum(time_entry(1:t));
end
Time(K+2)=TimeTotal;
%Time vector save the value of the moment that thief/cops show up
%the first value is for the thief, the last value is the time limitation 
%and the rest of them is for cops.

for j=Time(1):TimeTotal
     %generate a step's direction of the thief
    Tdirection=ceil(5*rand(1));
    % 1=North, 2=South, 3=East, 4=West, 5=staydi's
    
    % Execute step 2: no cops period
    % we calaute the thief's movement route by apply the value of thief's 
    % position,treasure's position, Anticlk and the fact that whether he 
    % had grab the tresure to the f_select function. This function is used
    % for choosing a proporate function to calcute the thief's continuing
    % move. 
    if j<Time(2)        % no cops    
         i=f_select(x(1),y(1),Trea(1),Trea(2),Anticlk,Tsuccess);
        if i==1 % no treasure, moving random
            [x_thief,y_thief]=normal_Move(x(1),y(1),Tdirection,RmSize);
        elseif i==2 % no treasure, approching the treasure
            [x_thief,y_thief,Tsuccess]=Catch(x(1),y(1),Trea(1),Trea(2));
        elseif i==3 % have treasure, approching the escape route
            [x_thief,y_thief,Esuccess]=Catch(x(1),y(1),Escape(1),Escape(2)); 
        end
    end
    
    % Execute step 3: cops enter
    %  after cops enter one by one, the problem becoming more complicated.
    %  If cops and thief prospect each other,they will change their
    %  movement function to the Catch.m and run_away.m respectively.   
    %  If the thief ptorospect or after grab treasure,he will move the 
    %  treasure or escape route directly while eacape from the cops'cature.
    if j>=Time(2) 
        for n=2:K+1
            if (j<Time(n+1)&&j>=Time(n))
                %assum cops do not porspect the thief now
                Cdirection=zeros(1,n-1);
                if isempty(x_thief)||isempty(y_thief)
                    dist=(x(1)-x(2:n)).^2+(y(1)-y(2:n)).^2;
                else
                    dist=(x_thief-x(2:n)).^2+(y_thief-y(2:n)).^2;
                end
                esp=find(dist<=Thief^2);
                i=f_select(x(1),y(1),Trea(1),Trea(2),Anticlk,Tsuccess);
                if i==1  % thief has not grabed treasure and moving random
                    if isempty(esp)==0     %At least one cop in the thief's visual field
                        [x_thief,y_thief]=run_away(x(1),y(1),x(esp+1),y(esp+1),RmSize);
                       %[x_thief,y_thief]=run_away2(x(1),y(1),x(esp+1),y(esp+1),RmSize);
                    else %There is no cop in the thief's visual field
                        [x_thief,y_thief]=normal_Move(x(1),y(1),Tdirection,RmSize);
                    end
                elseif i==2 % no treasure, approching the treasure
                    if isempty(esp)==0   % go to grab the with run away from cop
                        [x_thief,y_thief,Tsuccess]=TreaCatch(x(1),y(1),Trea(1),Trea(2),x(esp+1),y(esp+1));
                    else % go to grab the trea without prospectecd by cop
                        [x_thief,y_thief,Tsuccess]=Catch(x(1),y(1),Trea(1),Trea(2));
                    end
                elseif i==3 % have treasure, approching the escape route
                    if isempty(esp)==0  % approch to the eacape route with run away from cop
                        [x_thief,y_thief,Esuccess]=TreaCatch(x(1),y(1),Escape(1),Escape(2),x(esp+1),y(esp+1));
                    else % approch to the eacape route without prospectecd by cop
                        [x_thief,y_thief,Esuccess]=Catch(x(1),y(1),Escape(1),Escape(2)); 
                    end
                end
                %generate step's direction of cops
                for dir=1:n-1
                    % 1=North, 2=South, 3=East, 4=West, 5=stay
                    Cdirection(dir)=ceil(5*rand(1));
                end
                %the distance squire between the thief and every cop
                dis=(x(2:n)-x(1)).^2+(y(2:n)-y(1)).^2;
                %find which cop have prospected the thief and he will start
                %catching the thief
                In1=find(dis<=Cop(1:n-1).^2);
                %In1 is't a empty vector which means at least one cop
                %find the thief and strat catching him.
                if isempty(In1)==0 
                    for catc1=1:length(In1)
                        [x_cop(In1(catc1)),y_cop(In1(catc1)),Csuccess(catc1)]=Catch(x(In1(catc1)+1),y(In1(catc1)+1),x(1),y(1));
                        %In=[];
                    end
                    %the cop who knows the thief position, passes this
                    %message to the cops who in his sensor range.
                    for pass=1:n-1
                        %In1 is't a empty vector which means at least one cop
                        %is in this specific cop'sensor range.
                        if isempty(find(pass==In1, 1))==1
                           dis2=(x(pass+1)-x_cop(In1)).^2+(y(pass+1)-y_cop(In1)).^2;
                           % if the cop has been passed the thief possion
                           % he will catch the thief. 
                           if dis2<=min(Cop(pass),Cop(In1(catc1)))
                           [x_cop(pass),y_cop(pass),Csuccess(pass)]=Catch(x(pass+1),y(pass+1),x(1),y(1));
                           %if the cop still does not know the thief
                           %possition, he will keep on moving random.
                           else
                           [x_cop(pass),y_cop(pass)]=normal_Move(x(pass+1),y(pass+1),Cdirection(pass),RmSize);
                           end
                        %no cop in this specific cop' sensor range
                        else
                           continue
                        end
                    end
                %no cop find the thief, they just move random
                elseif isempty(In1)
                    [x_cop(1:n-1),y_cop(1:n-1)]=normal_Move(x(2:n),y(2:n),Cdirection,RmSize);
                end
                x(2:n)=x_cop(1:(n-1));
                y(2:n)=y_cop(1:(n-1));
            else
                continue
            end
        end
    end
    x(1)=x_thief;
    y(1)=y_thief;
    
    
    
    
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%    
%% Animation

% the thief has not got the treasure 
if Tsuccess==0
    % there is no cop
    if j<Time(2)
        %plot the thief position at time=j
        plot(x_thief,y_thief,'r.','MarkerSize',15);
        hold on
        %plot the treasure position
        plot(Trea(1),Trea(2),'g.','MarkerSize',15);
        %plot the escape slot position
        plot(Escape(1),Escape(2),'y.','MarkerSize',15);
        hold off
        legend({'Thief','Treasure'},'Location','EastOutside');
    % cops appear
    else
         %plot the thief position at time=j
        plot(x_thief,y_thief,'r.','MarkerSize',15);
        hold on
        %plot the cop position at time=j
        plot(x_cop,y_cop,'b.','MarkerSize',15);
         %plot the treasure and escape slot position
        plot(Trea(1),Trea(2),'g.',Escape(1),Escape(2),'y.');
        hold off
        legend({'Thief','Cops','Treasure'},'Location','EastOutside');
    end
    grid on
    axis([-RmSize-10 RmSize+10 -RmSize-10 RmSize+10]);
    %draw the boundary of the room with a thick black line
    h=rectangle('Position',[-RmSize,-RmSize,RmSize*2,RmSize*2]);
    set(h,'LineWidth',2);
    title('Game Simulation');
    f=getframe;
% the thief has got the treasure 
else
    % there is no cop
    if j<Time(2)
        %plot the thief position at time=j
        plot(x_thief,y_thief,'.','MarkerSize',15,'Color',[0.7 0.5 0]);
        hold on
        %plot the escape slot position
        plot(Escape(1),Escape(2),'y.','MarkerSize',15);
        hold off
        legend({'Thief','Escape'},'Location','EastOutside');
    % cops appear
    else
        %plot the thief position at time=j
        plot(x_thief,y_thief,'.','MarkerSize',15,'Color',[0.7 0.5 0]);
        hold on
        %plot the cop position at time=j
        plot(x_cop,y_cop,'b.','MarkerSize',15);
        plot(Escape(1),Escape(2),'y.','MarkerSize',15);
        hold off
        legend({'Thief','Cops','Escape'},'Location','EastOutside');        
    end
    grid on
    axis([-RmSize-10 RmSize+10 -RmSize-10 RmSize+10]);
    %draw the boundary of the room with a thick black line
    h=rectangle('Position',[-RmSize,-RmSize,RmSize*2,RmSize*2]);
    set(h,'LineWidth',2);
    title('Game Simulation');
    f=getframe;
end       



%% Termination and result

    % thief steal the treasure and escape
    if isempty(find(Csuccess,1))==0
        disp('Game over: Cops wins!');
        break
    %cop catch the thief 
    elseif Esuccess==1
        disp('Game over: Thiefs win!');
        break
    end
end
%exceed the time limiation cops have not catched the thief and the thief 
%have not escaped
if j==TimeTotal
    disp('Game over: Draw');
end
movie(f,5)















