%% Question 3
function [efMat,rank] = convertMat(M,m)
% convert mattrix M to echelon form
% M - the matrix need to convert
% m - mod variance
% efMat - result
efMat = ToReducedRowEchelonForm(M,m);
% rank of matrix
rank = length(find(any(efMat,2)==1));
disp(strcat('rank:',num2str(rank)));
disp('row space:');
disp(efMat(1:rank,:));
end