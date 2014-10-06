function centre = findCentre(planar, spatial)
% Function findCentre(planar, spatial)
% planar - PlanarPoints
% spatial - SpatialPoints
% centre - focus point

A = zeros(3,3);
B = zeros(3,1);

for i = 1:size(planar,2)
    q = spatial(:,i);
    p = planar(:,i);
    diff = q - p;
    unitDir = diff ./ sqrt((dot(diff,diff)));
    I = eye(3);
    D = I - (unitDir * unitDir');
    A = A + (D' * D);
    B = B + ((D' * D) * p);
end

centre = A \ B;




