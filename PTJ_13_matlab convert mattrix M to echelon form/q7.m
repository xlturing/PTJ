%% Question 7
function [basUAddW,basUAndW] = q7(A,B)
% question 7 solution
% A - matrix A
% B - matrix B
% basUW - basis of U+W
% basU0W0 - basis of U0+W0
% get U and W
[efMatA,rankA] = convertMat(A);
[efMatB,rankB] = convertMat(B);
U = efMatA(1:rankA,:);
W = efMatB(1:rankB,:);
% get U0 and W0, then get basis of U+W
U0 = findKer(U);
W0 = findKer(W); 
basUAddW = findKer(intersect(U0,W0,'rows'));
disp('basis of U+W');
disp(basUAddW);
% get basis of U0 add W0. then get basis of U0 and W0
U00 = findKer(U0);
W00 = findKer(W0);
basU0AddW0 = findKer(intersect(U00,W00,'rows'));
basUAndW = findKer(basU0AddW0);
disp('basis of U and W');
disp(basUAndW);
end