function [area, obstacles, entry, numS, rangeS, speedS, entryTime, timeLimit] = fileInput(path)
% Function: 
% read the initial configuration from the config file
% Inputs: 
% -path the path of the config file 
% Outputs: 
% -area size of rectangular area, in metres (config file is kms,we will
% convert to metres)
% -obstacles if there isn't obstacles, this is -1; else this is a 
% martrix in which a row represent a obstacle, and this matrix have
% 5 columns, which the first of colunm is a flag: 1 means square, 2 means 
% circle, 3 means rectangle. (the last column of circles and squares is -1,
% because rectangles need 4 columns (length and width for rectangle and 
% co-ordinates)).
% -entry side of entry square, in metres
% -numS mumber of sensors
% -rangeS range of each sensor (radius, in metres)
% -speedS maximum speed of each sensor (in metres/sec)
% -entryTime time limit for each sensor's entry (in number of secs, an integer)
% -timeLimit time limit for simulation (number of secs, an integer)
fidin=fopen(path,'r');
nline=1;
area = zeros(1, 2);
temp = zeros(1, 6);
iTemp = 1;
while ~feof(fidin)
    tline=fgetl(fidin);
    if(tline(1) ~= '#')
        S = regexp(tline, '\s+', 'split'); % split string by space
        % read area
        if (size(S,2) == 2)
            area(1) = str2double(S(1)) * 1000;
            area(2) = str2double(S(2)) * 1000;
        elseif (nline == 4)
            numObst = str2double(S(1)); % the number of obstacles
            if(numObst == 0)
                obstacles = -1; % don't have obstacles
            % read obstacles
            else
                obstacles = zeros(numObst, 5);
                i = 1;
                while(i <= numObst)
                    tline = fgetl(fidin);
                    nline = nline + 1;
                    if(tline(1) == '#')
                        continue;
                    end
                    S = regexp(tline, '\s+', 'split'); % split string by space
                    if(strcmp(S(1),'square') == 1)
                        obstacles(i, 1) = 1;
                        obstacles = readAObst(S, obstacles, i);
                        obstacles(i,5) = -1;
                    elseif(strcmp(S(1),'circle')==1)
                        obstacles(i,1) = 2;
                        obstacles = readAObst(S, obstacles, i);
                        obstacles(i,5) = -1;
                    elseif(strcmp(S(1),'rectangle')==1)
                        obstacles(i,1) = 3;
                        obstacles = readAObst(S, obstacles, i);
                        obstacles(i,5) = str2double(S(5));
                    end
                    i = i + 1;
                end
            end
        else
            temp(iTemp) = str2double(S(1));
            iTemp = iTemp + 1;
        end
    end
    nline = nline + 1;
end
fclose(fidin);
entry = temp(1);
numS = temp(2);
rangeS = temp(3);
speedS = temp(4);
entryTime = temp(5);
timeLimit = temp(6);

%% read a obstacle
function M = readAObst(S, obstacles, i)
    for j = 2 : 4
        obstacles(i, j) = str2double(S(j));
    end
    M = obstacles;
end
end

