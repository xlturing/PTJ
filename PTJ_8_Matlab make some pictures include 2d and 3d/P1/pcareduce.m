function [approxcomp,approxvec]=pcareduce(new_data,approxnum,meanvec,evecmat)
% [APPROXCOMP,APPROXVEC]=PCAREDUCE(NEW_DATA, APPROXNUM,MEANVEC, EVECMAT)

% reduces new data based on a Principal Components Analysis
% (PCA) of initial data. Inputs are:
% NEW_DATA - a column vector to be approximated
% APPROXNUM - a scalar giving the order of the approximation
% MEANVEC - the PCA mean vector (from PCAPRELIM)
% EVECMAT - the eigenvectors of the PCA(from PCAPRELIM)
%
% Return values are:
% APPROXCOMP - the components as a row vector of scalars
% APPROXVEC - the approximation of the new data as a vector

% Set up the return values
approxcomp = zeros(1, approxnum);
approxvec = meanvec;

adj_data = new_data-meanvec;

% Loop through the eigenvectors, finding the components
% and building the approximation
for i=1:approxnum
    evec = evecmat(:,i);
    beta = dot(adj_data, evec);
    approxcomp(i) = beta;
    approxvec = approxvec + beta*evec;
end