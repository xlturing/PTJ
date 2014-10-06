function [focus,endPts] = focusPt(data1,data2,size)
%   finds the closest point between two lines
%   Inputs: data1/2 - Planar/Spatial data
%           size - number of columns in each set of data
%   Outputs: focus - set of all focus points
%            endPts - end points useful for plotting data
focus = [];
endPts = [];
for i=1:(size-1)
    v1 = data1(:,i)-data2(:,i);
    p1 = data1(:,i);
    for n=i+1:size
        v2 = data1(:,n)-data2(:,n);        
        p2 = data1(:,n);
        a = [v1,-v2];
        b = [p2-p1];
        ts = a\b;
        t = ts(1,1);
        s = ts(2,1);
        focus =[focus,(p1+t*v1),(p2+s*v2)]; 
    end
    endPts = [endPts,(p1+(t-i)*v1)];    % (t-i) extends the lines
end
endPts = [endPts,(p2+(s-5)*v2)];

