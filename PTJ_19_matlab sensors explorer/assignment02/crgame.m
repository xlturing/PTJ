% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 8th.
% Estimated working time on this assignment: 25h.

% Functionality of this file: 
% This file asks the user to choose one of the configuration files
% (provided by the user) as game settings. If every value is valid, for
% example, the entry radius for characters is no greater than half the room
% side length, the game will start. If any invalid value is detected, it
% will ask the user to input a valid one as replacement. In order to ignore
% typos, all parameters that should be NATURAL numbers are taken the
% absolute value and rounded after being converted from unit METER to
% CENTIMETER.
% The game has a time limit from the configuration file. When game starts,
% the entry circle will show up, indicating the possible entry positions
% for characters, together with the green treasure point and yellow escape
% spot for the thief. The thief (red point) enters the room randomly within
% a time limit from the configuration file. Then each cop (blue point)
% enters randomly as well one by one within same time limit counting from
% the previous person. Once every one enters, the entry circle will
% disappear because it is not needed any more.
% [ABOUT the thief]
% Once the thief enters, he starts searching for treasure at any possible
% spot in the room with a strategy. He moves to a specific starting point,
% which is next to the east wall and has a distance of his anti-cloaking
% radius to the south wall. And from that starting point, his strategy
% begins and he will move in "S" route, intending to let his anti-cloaking
% device to cover the entire room. After he locates the treasure, he takes
% the treasure (color changed to brown) as quickly as possible and then
% moves to the escape spot. In any type of the thief's movement, he pays
% attention to cops in his detecting range and tries to avoid them as well
% as maintain his strategy. If, unfortunately, he gets caught, both him and
% the user know that game is over.
% [About cops]
% Once cops enter, each of them moves randomly in the room if he does not
% locate the thief. If cop A locates the thief, he will chase the thief. If
% cop B and he himself happen to be in each other's range, then cop B also
% knows the location of the thief and starts chasing. Once any cop catches
% the thief, both the cop and the user know that game is over.

% Design approach:
% [Parameters assignment]
% How the numeric data is obtained from the text file is hard to think of
% at the beginning until we found the orderliness of the data (the value
% name and the value are stored in turn) and all value name line begin with
% "#". We firstly obtain all the lines into a cell array as texts and then
% got rid of lines starting with "#". Meanwhile, if any value name is
% missing under its name, we would ask for one. Then a new text file,
% Parameters.txt, was created. It contained all numeric values. We only
% needed to read them out into matrix "param" and assigned all parameters.
% If during the assigning process any invalid value has been detected, the
% user would be asked for a valid one.
% [Coordinates]
% The coordicates for the thief and cops are stored separately in two cell
% arrays. The number of rows for both equals the number of characters; the
% number of columns equals the number of steps.
% [System]
% The main loop is time/steps. Within it thief and cops have their
% movements. There is a 2nd-level loop for each cop's coordinate updates.
% The coordinate updats for the thief is within the main loop. In each
% turn, the change of movement for every character is calculated by sub
% functions. And the new coordinates can be found by adding the change to
% the previous coordinates. These are then stored in the coordinate
% matrices. There is also a warning system, controlling the status of the
% game internally. The user will be informed of two of the statuses: 1
% (thief caught) & 3 (thief escapes).
% [Plot]
% The latest positions of thief and cops are plotted using "drawnow". In
% order to delete the previous points meanwhile keeping the fluency of
% plotting, we combined "hold on" and "cla". Plot title and room size are
% displayed.

clc;
close all;
clear;

%Read the config file
fileName = input('Please provide a file name:\n', 's');
fid = fopen(fileName, 'r');
ln = fgetl(fid);
index_1 = 1;
while ischar(ln)
    text{index_1} = ln;
    ln = fgetl(fid);
    index_1 = index_1 + 1;
end

fclose(fid);
index_2 = 1;
for i = 1:index_1 - 1
    if isempty(text{i} ~= '#') == 0
        if text{i}(1) ~= '#'
            data{index_2} = text{i};
            index_2 = index_2 + 1;
        end
    else
        fprintf('Please provide the value missing for \n%s:\n', text{i-1});
        data{index_2} = input('', 's');
        index_2 = index_2 + 1;
    end
end

ofh = fopen('Parameters.txt', 'w');
for i = 1:max(size(data))
    s = strcat(data{i}, '\n');
    fprintf(ofh, s);
end

fclose(ofh);
param = textread('Parameters.txt');

% Assign parameters:
size = abs(param(1, 1))*100; % m -> cm

r_in = abs(param(2, 1))*100; % m -> cm
while abs(r_in) > size / 2
    r_in = input('Please provide a valid entry circle radius, in cm:\n');
end

x_trs = param(3, 1)*100; % m -> cm
while abs(x_trs) > size / 2
    x_trs = input('Please provide a valid x-coord for treasure, in cm:\n');
end

y_trs = param(3, 2)*100; % m -> cm
while abs(y_trs) > size / 2
    y_trs = input('Please provide a valid y-coord for treasure, in cm:\n');
end

r_anticl = abs(param(4,1)); %cm

x_e = param(5, 1)*100; % m -> cm
while abs(x_e) > size / 2
    x_e = input('Please provide a valid x-coord for escape spot, in cm:\n');
end

y_e = param(5, 2)*100; % m -> cm
while abs(y_e) > size / 2
    y_e = input('Please provide a valid y-coord for escape spot, in cm:\n');
end

t_total = abs(round(param(6, 1))); %steps

t_in_max = abs(round(param(7, 1))); %steps
while abs(t_in_max) > t_total
    t_in_max = input('Please provide a valid maximum entry time:\n');
end

r_thief = abs(param(8, 1)); %cm
n_cop = abs(round(param(9, 1)));

% Sensor range for cops:
r_cop = zeros(1, n_cop);
if n_cop > length(param)-9
    fprintf('You need to give sufficient number of cop ranges.\n');
    fprintf('Modify your file and re-start the game.\n');
    return;
else
    for i = 1:param(9)
        r_cop(i) = param(9+i, 1);
    end
end

% Entry time for the thief:
in_time_T = round(rand*t_in_max);
if in_time_T == 0
    in_time_T = 1;
end

% Entry coords for the thief:
theta = 2*pi*rand;
x_in_T = round(r_in*cos(theta));
y_in_T = round(r_in*sin(theta));

% in time for cops:
in_time_C = zeros(n_cop, 1);
in_time_C(1) = in_time_T + round(rand*t_in_max); % The 1st cop enters
for N = 2:n_cop
    in_time_C(N) = in_time_C(N-1) + round(rand*t_in_max);
end

% Create coords cell for thief and cops respectively: Cells
crd_T = cell(t_total, 1);
crd_C = cell(n_cop, t_total);

% Warn: 0 -> game goes on;
%       1 -> thief gets caught;
%       2 -> thief starts strategic movement;
%       3 -> thief escapes the room.
warn_T = 0;
warn_C = 0;
who_knows = 0; % Initially no cop locates the thief
T_has_trs = 0; % Initially the thief does not take the treasure

for t = 1: t_total
    if t == in_time_T
        crd_T(t) = {[x_in_T, y_in_T]};
        crd_C(:, t) = {[0, 0]};
    elseif t > in_time_T
        % Cops movements:
        for N = 1: n_cop
            dir = round(rand*5);
            if t == in_time_C(N)
                theta = 2*pi*rand;
                crd_C(N, t) = {[round(r_in*cos(theta)), ...
                                round(r_in*sin(theta))]};
            elseif t > in_time_C(N)
                crd_C_prev = cell2mat(crd_C(N, t - 1));
                [dx_C, dy_C, warn_C, who_knows] = c_move(N, size, t, ...
                                                  r_cop, crd_C, crd_T, ...
                                                  dir, who_knows);
                crd_C(N, t) = {[dx_C + crd_C_prev(1), ...
                                dy_C + crd_C_prev(2)]};
            else
                crd_C(N, t) = {[0, 0]};
            end
        end
        crd_C_now = cell2mat(crd_C(:,t));
        crd_T_prev = cell2mat(crd_T(t - 1));
        
        for i = 1:n_cop
            if crd_T_prev == crd_C_now(i,:)
                warn_C = 1;
            end
        end
        if warn_C == 1
            fprintf('Game over!\nThe thief was caught at step %d.\n', t);
            return;
        end
        
        if crd_T_prev == [round(x_trs) round(y_trs)]
            T_has_trs = 1;
        end
        
        if T_has_trs == 1 % Thief takes the treasure
            [dx_T, dy_T, warn_T] = t_escape(t, crd_T, x_e, y_e, crd_C,...
                                            size, r_thief);
            plot(thief(1),thief(2), 'o','Color',[0.502 0.251 0], ...
                 'MarkerFaceColor', [0.502 0.251 0]);
            
        else % Thief doesnt take the treasure yet
            if warn_T ~= 2 % Before thief gets to the start of strategy
               [dx_T, dy_T, warn_T] = t_to_start(size ,crd_T, crd_C, ...
                                      t, r_anticl, r_anticl, x_trs, y_trs);
            else % Thief gets to start point
                [dx_T, dy_T, warn_T] = t_strat(size ,crd_T, crd_C, t, ...
                                       r_anticl, r_thief, x_trs, y_trs);
            end
            
            plot(thief(1), thief(2),'ro','MarkerFaceColor', 'r');
            plot(x_trs, y_trs, 'go', 'MarkerFaceColor', 'g');
            axis square;
            axis([-size / 2 size / 2 -size / 2 size / 2]);
        end
        
        if warn_T == 3 % Thief escapes the room
            fprintf('Game over!\nThe thief escapes at step %d.\n', t);
            plot(x_e, y_e, 'o','Color', [0.502 0.251 0], ...
                'MarkerFaceColor', [0.502 0.251 0]);
            axis square;
            axis([-size / 2 size / 2 -size / 2 size / 2]);
            set(gca, 'linewidth', 3);
            return;
        end
        crd_T(t) = {[dx_T + crd_T_prev(1), dy_T + crd_T_prev(2)]};
    else % Before thief enters
        crd_C(:,t) = {[0,0]};
        crd_T(t) = {[0,0]};
        plot(x_e, y_e, 'o','Color', [0.502 0.251 0], 'MarkerFaceColor',...
             [0.502 0.251 0]);
        plot(x_trs, y_trs, 'go', 'MarkerFaceColor', 'g');
    end
    thief = cell2mat(crd_T(t));
    cop = cell2mat(crd_C(:,t));
    plot(cop(:,1), cop(:,2), 'bo', 'MarkerFaceColor', 'b');
    if t < in_time_C(n_cop)
        plot(0,0,'wo', 'MarkerFaceColor', 'w');
        theta = linspace(0, 2*pi, 100);
        plot(r_in*cos(theta), r_in*sin(theta), '.', 'Color', ...
             [0.7 0.1 0.2], 'MarkerSize', 5);
        hold on;
    end
    
    plot(x_e, y_e, 'yo', 'MarkerFaceColor', 'y');
    axis square;
    axis([-size / 2 size / 2 -size / 2 size / 2]);
    title('Thief, Treasure And Cop Game', 'FontSize', 15);
    label = sprintf('Side Length = %dcm', size / 2);
    xlabel(label, 'FontSize', 15);
    set(gca, 'linewidth', 3);
    box on;
    drawnow;
    hold on;
    cla;
end
fprintf('Time is up.') % When time runs out