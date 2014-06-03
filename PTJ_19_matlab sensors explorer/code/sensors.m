function sensors
clc;
close all;
clear;

%% Read the config file
try
    % path = input('Please provide the config file path:\n', 's');
    [area, obstacles, entry, numS, rangeS, speedS, entryTime, timeLimit] = fileInput('test.txt');
    % show step
    handleStep = text(area(1)+20,area(2)/2+10,'step: 0');
catch err
    error('Read config file fail!');
end

%% variable definition 
handleWalls = zeros(1,4);                                                   % the handle of the four walls(north, south, west, east)
handleObsts = zeros(1,size(obstacles,1));                                   % the handle of the object which represent obstacles here
handleEntry = 0;                                                            % the handle of entry square
sensors = struct('handle',1,'dir',pi,'avoid_x',0,'avoid_y',0,'state',0);    % the structs of all sensors(include: handle; direction: angle from x(e.g pi/2)
                                                                            % avoid_x,avoid_y: the moving distaces for avoiding obstacles; state: <0,1> <move,stop>                                                            
handleRange = 0;                                                            % the handle of the range of a sensor
sensorStates = zeros(1,numS);                                               % the state of sensors
step = 0;                                                                   % record the steps(second)
numLegend = 1;                                                              % number of legend
interval = 0.01;                                                             % step interval(default is 1 second)

%% Init game
% Draw rectangular area
xMin = 0;
xMax = area(1);
yMin = 0;
yMax = area(2);
% North and South
x_ns = xMin:0.1:xMax;
temp_size = size(x_ns,2);
y_north(1:temp_size) = yMax;
y_south = zeros(1,temp_size);
% West and East
y_we = yMin:0.1:xMax;
temp_size = size(y_we,2);
x_west = zeros(1,temp_size);
x_east(1:temp_size) = xMax;
hold all
label(numLegend) = plot(x_ns,y_north,'LineWidth',1,'color','k');  %North Wall
plot(x_ns,y_south,'linewidth',1,'color','k');  %South Wall
plot(x_west,y_we,'linewidth',1,'color','k');   %West Wall
plot(x_east,y_we,'linewidth',1,'color','k');   %East Wall  
axis([xMin,xMax,yMin,yMax]);                   %Axis
grid on                                        %Grid
title('Mobile Robotic Sensors')                %Title 
xlabel('metre');
ylabel('metre');
value(numLegend) = {'walls'};
numLegend = numLegend +1;
% Draw obstacles
for i = 1 : size(obstacles,1) % A row represent a obstacle
    switch obstacles(i,1)
        case 1          % 1 means square
            handleObsts(i) = rectangle('Position',[obstacles(i,3),obstacles(i,4),obstacles(i,2),obstacles(i,2)],'FaceColor','b');
        case 2          % 2 means circle
            handleObsts(i) = rectangle('Position',[obstacles(i,3),obstacles(i,4),obstacles(i,2),obstacles(i,2)],'Curvature',[1,1],'FaceColor','b');
        case 3          % 3 means rectangle 
            handleObsts(i) = rectangle('Position',[obstacles(i,4),obstacles(i,5),obstacles(i,2),obstacles(i,3)],'FaceColor','b');
    end
end
% Draw entry square
handleEntry = rectangle('Position',[area(1)/2-entry/2,area(2)/2-entry/2,entry,entry], 'LineWidth',2,'LineStyle','--');
label(numLegend)=plot(nan,nan,'s','markeredgecolor',get(handleEntry,'edgecolor'),...
 'markerfacecolor',get(handleEntry,'facecolor'));
value(numLegend) = {'entry'};
numLegend = numLegend + 1;
if(obstacles(1)~=-1)
    label(numLegend)=plot(nan,nan,'s','markeredgecolor',get(handleObsts(1),'edgecolor'),...
     'markerfacecolor',get(handleObsts(1),'facecolor'));
    value(numLegend) = {'obstacle'};
    numLegend = numLegend + 1;
else
    handleObsts(1) = -1;
end
legend_h = legend(label,value,'Location','NorthEastOutside');


%% Introduce the sensors into the area
angle = 2*pi/numS;                      % every sensor direction
dirS = 0;                               % the angle of each sensor
flag = 0;                               % legend flag
for i = 1:numS
    % create new sensors
    sensors(i) = newSensor(area, dirS);
    if(flag == 0)
        label(numLegend)=plot(nan,nan,'s','markeredgecolor',get(sensors(1).handle,'edgecolor'),...
        'markerfacecolor',get(sensors(1).handle,'facecolor'));
        value(numLegend) = {'sensor'};
        numLegend = numLegend + 1;
        legend_h = legend(label,value,'Location','NorthEastOutside');
        flag = 1;
    end
    dirS = dirS + angle;
    % show the range of sensor
    handleRange = getSensorRange(sensors(1), rangeS);
    pause(interval/10);
    delete(handleRange);
    % generate random entry time
    random = unidrnd(entryTime);
    %move sensors in the area
    for j = 1:random
        [sensors,allSensorsStop] = moveSensor(sensors, rangeS, i, speedS, area, handleObsts); % move current sensors in the area
        step = step + 1;
        set(handleStep,'String', ['step: ',num2str(step)]);
        pause(interval);
    end 
end

%% move all sensors to perimeter
temp = step;
suc = 0;
for i = temp:timeLimit
    [sensors,allSensorsStop] = moveSensor(sensors, rangeS, numS, speedS, area, handleObsts); % move current sensors in the area
    step = step + 1;
    set(handleStep,'String', ['step: ',num2str(step)]);
    pause(interval);
    % If all sensors stop(all arrive perimeter), then start to adjust
    if(allSensorsStop == 1)
        suc = 1;
        break;
    end
end
%% adjust sensors to cover more

if(suc == 1)
    suc = 0;
    temp = i;
    for i = temp:timeLimit
        if(step==430)
            disp('haha');
        end
        [sensors, adEnd] = adjustSensors(sensors, area, rangeS, speedS, handleObsts);
        if(adEnd == 1)
            suc = 1;
            break;
        else
            % adjust then move all sensors
            while(1)
                [sensors,allSensorsStop] = moveSensor(sensors, rangeS, numS, speedS, area, handleObsts);
                step = step + 1;
                set(handleStep,'String', ['step: ',num2str(step)]);
                pause(interval);
                if(allSensorsStop == 1)
                    i = step;
                    break;
                end
            end
        end
    end
    step = step + 1;
    set(handleStep,'String', ['step: ',num2str(step)]);
    pause(interval);
end
%% result output
if(suc == 0)
    disp('Sorry! These sensors cannot cover all perimetre');
else
    disp('Congradulation! Cover end!');
end
