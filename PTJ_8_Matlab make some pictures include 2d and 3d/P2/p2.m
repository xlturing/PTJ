clc;
load a2q2data;

%% problem2 a
%% plot PlanarPoints and SpatialPoints scatter3
scatter3(PlanarPoints(1,:),PlanarPoints(2,:),PlanarPoints(3,:),'bd','MarkerFaceColor','b');
hold all;
scatter3(SpatialPoints(1,:),SpatialPoints(2,:),SpatialPoints(3,:),'go','MarkerFaceColor','g');
legend('PlanarPoints','SpatialPoints','Location','NorthEast');
title('PlanarPoints and SpatialPoints');
xlabel('x'),ylabel('y'),zlabel('z');

%% plot original data and centre
centre = findCentre(PlanarPoints, SpatialPoints)
figure;
%plotData(PlanarPoints,SpatialPoints,centre)
[focus,endPts] = focusPt(PlanarPoints,SpatialPoints,size(SpatialPoints,2));
plotLines(PlanarPoints,endPts,centre,size(SpatialPoints,2))
title('Original data');

%% plot  deviated data and new centre
for i = 1:size(PlanarPoints,1)
    for j = 1:size(PlanarPoints,2)
        newPlanar(i,j) = PlanarPoints(i,j) * rand(1) * mean2(PlanarPoints);%Apply small random deviations to the planar data
    end
end
% the new intersection after random deviation
newCentre = findCentre(newPlanar, SpatialPoints)
figure;
%plotData(PlanarPoints,SpatialPoints,newCentre)
[focus,endPts] = focusPt(newPlanar,SpatialPoints,size(SpatialPoints,2));
plotLines(newPlanar,endPts,newCentre,size(SpatialPoints,2))
title('Deviated data');

%% problem2 b
[diffCen,diffRad] = sphereFitDiff(150,[500,500,500]);