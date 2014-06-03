function [RmSize,Radius,Trea,AnticlK,Escape,TimeTotal,TimeEnter,Thief,K,Cop]=data_input(s)
% data_input function is a function to import the parameters from a specific file;
% additionally, it is not numerical file and a line starting with a '#'
% should be treated as a comment line and ignored. this function need to 
% read the file and extract the required parameters. moreover, the error 
% checking and report, including the incorrect and insufficient number given
% is nessuary.


%open the file and do preparation
fh=fopen(s,'r');
Data=[];
i=1;
ln='';

% Import the value from the file
% check whether the ln is a character
while ischar(ln)
    %let ln equals to the input file
    ln=fgets(fh);
    % check whether the ln is a character
    if ischar(ln)
        % check whether the ln is a '#'
        % when ln is a '#', find the space in the file 
        if ln(1:1)~='#'
            n=find(ln==' ');
            % when there is no space before import the data and check
            % whether it is a charater in the loop
            if n~=0
            Data(i)=str2double(ln(1:n));
            Data(i+1)=str2double(ln(n:length(ln)));
            i=i+2;
            else 
            Data(i)=str2double(ln);
            i=i+1;
            end
        else
            continue
        end
    end
end
fclose(fh);


%% Check the validation of the input number 

% Size of room
RmSize=Data(1);
% Size of room is a positive real number
if isreal(RmSize)==0 || RmSize<0
    error('Invalid Input. Positive real number only.');
else
    RmSize=100*RmSize;
end
RmSize=RmSize/2;

% Radius of entry circle
Radius=Data(2);
% Radius of entry circle is a Positive real number
if isreal(Radius)==0 || Radius<0
    error('Invalid Input. Positive real number only.');
else
    Radius=100*Radius;
end

% Position of Trea
x_Trea=Data(3);
y_Trea=Data(4);
% Position of Trea is two Real number
if isreal(x_Trea)==0 || isreal(y_Trea)==0
    error('Invalid Input. Real number only.');
else
    % transform the units from meter to centimeter
    x_Trea=100*x_Trea;
    y_Trea=100*y_Trea;
end
Trea=[x_Trea,y_Trea];

% Range of anti-cloaKing device
AnticlK=Data(5);
% Range of anti-cloaKing device is a Positive real number
if isreal(AnticlK)==0 || AnticlK<0
    error('Invalid Input. Positive real number only.');
end

% Position of Escape route
x_Escape=Data(6);
y_Escape=Data(7);
% Position of Escape route is two Real number 
if isreal(x_Escape)==0 ||  isreal(y_Escape)==0
    error('Invalid Input. Real number only.');
else
    % transform the units from meter to centimeter
    x_Escape=100*x_Escape;
    y_Escape=100*y_Escape;
end
Escape=[x_Escape,y_Escape];

% Time limit for simulation
TimeTotal=Data(8);
% Time limit for simulation is a Positive real number
if isreal(TimeTotal)==0 || TimeTotal<0
    error('Invalid Input. Positive real number only.');
end

% Time limit for each person's entry
TimeEnter=Data(9);
% Time limit for each person's entry is a Positive integer
if mod(TimeEnter,1)~=0 || TimeEnter<0
    error('Invalid Input. Positive integer only.');
end

% Thief sensor range
Thief=Data(10);
% Thief sensor range is a Positive integer 
if mod(Thief,1)~=0 || Thief<0
    error('Invalid Input. Positive integer only.');
end

% Number of Cops
K=Data(11);
% Number of Cops is a Positive integer
if mod(K,1)~=0 || K<0
    error('Invalid Input. Positive integer only.');
end

% Cop sensor range
Cop=zeros(1,K);
%with number of length(Data)-11
if length(Data)-11==K
    %Import values
    for i=1:K
        Cop(i)=Data(11+i);
        % Cop sensor range is a group of Positive integer
        if mod(Cop(i),1)~=0 || Cop(i)<0
            error('Invalid Input. Positive integer only.');
        end
    end
else error('Insufficient Input.');
end
end
