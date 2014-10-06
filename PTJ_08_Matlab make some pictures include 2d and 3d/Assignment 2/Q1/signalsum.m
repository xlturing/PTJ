function [sumSignals] = signalsum(signals,size)
% Input: signals - a matrix of signals, displayed as column vectors
%        size - number of signals
% Output: sumSignals - sum of the signals, organized into row vectors
sumSignals=[];
for i=1:size
    total=sum(signals(:,i));
    sumSignals=[sumSignals,total];
end