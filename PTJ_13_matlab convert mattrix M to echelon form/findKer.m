%% Question 4
function [ker] = findKer(M)
% find kernel of M
% M - input matrix
% ker - return result
[efMat,rank] = convertMat(M); % echelon form
c = size(efMat,2); % columns of efM
r = size(efMat,1); % rows of efM
I = eye(c);
A = cat(1,efMat,I); % concat efMat and I
% column echelon 
for i = 1:rank
    A([i;r+i],:)=A([r+i;i],:);
end
A = A(r+1:r+c,:);
ker = A(:,c-rank+1:c);
disp('a basis of the null space of A');
disp(ker);
