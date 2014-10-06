function [var] = uniform(data,size)
% finds the variation by using uniform distribution
% Inputs: data - must be a row vector
%         size - number of columns in the row vector
max = data(1,1);
min = data(1,1);
for i=2:size
    if data(1,i)>max
        max = data(1,i);
    elseif data(1,i)<min
        min = data(1,i);
    end
end
var = (1/12)*((max-min)^2);
    