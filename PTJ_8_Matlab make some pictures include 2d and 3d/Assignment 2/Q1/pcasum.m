function [tk] = pcasum(matrix,size)
% function for calculating tk (used to calculate the coverage)
% input: eigenvectors
% size: number of signals stored in column vectors
% tk: sum of all elements in matrix (tk)

tk = 0;
for i = 1:size
    tk = tk+sum(matrix(i,1));
end
