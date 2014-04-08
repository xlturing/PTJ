function [minFocus] = minDist(data1,data2,focus,col)
%   finds the focus point of the lines
%   Inputs: focus - set of focus points found by running focusPt
%           col - number of columns in data1/2
minD = 0;
minFocus = [0,0,0];
n = size(focus,1);
for i=1:n
    P = focus(:,i);
    for b=1:col
        Q1 = data1(:,b);
        Q2 = data2(:,b);
        dist = abs(cross(Q1-Q1,P-Q1))/abs(Q2-Q1);
        if dist<=minD
            minD = dist;
            minFocus = P;
        end
    end
end
