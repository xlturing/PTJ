function [evalvec, meanvec, evecmat] = pcaprelim(Z)
% Taken from the CISC 271 course note
% FUNCTION [EVALVEC, MEANVEC, EVECMAT] = PCAPRELIM(Z_DATA)
% performs the preliminary Principal Components Analysis
% (PCA) of A, a matrix in which the data are
% represented as columns. PCAPRELIM returns:
% EVALVEC - eigenvalues of the PCA, sorted in decreasing order
% MEANVEC - the mean vector of the initial data
% EVECMAT - the eigenvectors of the PCA, as column vectors
% Find the mean vector and form it into a matrix
[m,n] = size(Z);
meanvec = mean(Z,2);
M = meanvec*ones(1,n);
% Find the difference matrix and the covariance matrix
D = Z - M;
C=D*D'/(m-1);
% Find the eigenvectors as a matrix and
% the eigenvalues as a vector
[Vvecs, Vvals] = eig(C);
Vdiag = diag(Vvals);
% Find the indexing that sorts the eigenvalues
% from the dominant down to the smallest
[temp, Vval_index] = sort(Vdiag, 1, 'descend');
% Sort the original decomposition into the return variables
evalvec = Vdiag(Vval_index);
evecmat = Vvecs(:, Vval_index);
