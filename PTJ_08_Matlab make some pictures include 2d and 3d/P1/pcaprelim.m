function [evalvec, meanvec, evecmat, coverage] = pcaprelim(first_data)
% FUNCTION [EVALVEC, MEANVEC, EVECMAT] = PCAPRELIM(FIRST_DATA)
% performs the preliminary Principal Components Analysis
% (PCA) of FIRST_DATA, a matrix in which the data are
% represented as columns. PCAPRELIM returns:
% EVALVEC - eigenvalues of the PCA, sorted in decreasing order
% MEANVEC - the mean vector of the initial data
% EVECMAT - the eigenvectors of the PCA, as column vectors
% COVERAGE - 

% Convert to row format, find the mean vector,
% form it into a matrix
A = first_data';
Amean = mean(A);
M = repmat(Amean, size(A,1), 1 );
meanvec = Amean';

% Find the difference matrix and the covariance matrix;
D = A - M;
C = cov(D);

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

% caulate the coverage
[coeff score] = princomp(D);
coverage = cumsum(var(score)) / sum(var(score));