function fig = plotData(planar, spatial, centre)
% Function plotData(PlanarPoints, SpatialPoints, centre)
% planar - PlanarPoints
% spatial - SpatialPoints
% centre - get by findCentre

scatter3(planar(1,:),planar(2,:),planar(3,:),'bd','MarkerFaceColor','b');
hold on;
scatter3(spatial(1,:),spatial(2,:),spatial(3,:),'go','MarkerFaceColor','g');
hold on;
scatter3(centre(1,:),centre(2,:),centre(3,:),'ro','MarkerFaceColor','r');
hold on;
for i=1:size(planar,2)
    X = [planar(1,i),centre(1,:)];
    Y = [planar(2,i),centre(2,:)];
    Z = [planar(3,i),centre(3,:)];
    fig = plot3(X,Y,Z);
end
hold on;
for i=1:size(spatial,2)
    X = [spatial(1,i),centre(1,:)];
    Y = [spatial(2,i),centre(2,:)];
    Z = [spatial(3,i),centre(3,:)];
    fig = plot3(X,Y,Z);
end