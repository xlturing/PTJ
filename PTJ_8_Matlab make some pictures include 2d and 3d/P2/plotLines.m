function [fig] = plotLines(data1,data2,centre,size)
% Inputs: data1 - Planar points
%         data2 - Spatial points
%         size - size of the the two files (number of points)
% Output: fig - plot
fig = scatter3(data1(1,:),data1(2,:),data1(3,:),'bd','MarkerFaceColor','b');
hold on;
fig = scatter3(data2(1,:),data2(2,:),data2(3,:),'go','MarkerFaceColor','g');
hold on;
fig = scatter3(centre(1,:),centre(2,:),centre(3,:),'ro','MarkerFaceColor','r');
hold on;
for i=1:size
    X = [data1(1,i),data2(1,i)];
    Y = [data1(2,i),data2(2,i)];
    Z = [data1(3,i),data2(3,i)];
    fig = plot3(X,Y,Z);
end
    