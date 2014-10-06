function [approxcomp,approxvec] = pcaapprox(new_data,approxnum,meanvec,evecmat)
% Taken from the CISC 271 course note
% [APPROXCOMP,APPROXVEC]=PCAAPPROX(NEW_DATA, APPROXNUM,MEANVEC, EVECMAT)
% approximates new data based on a Principal Components Analysis
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
diffvec = new_data - meanvec;
approxcomp = zeros(approxnum, 1);
approxvec = meanvec;
% Loop through the eigenvectors, finding the components
% and building the approximation
for i=1:approxnum
    evec = evecmat(:,i);
    beta = dot(diffvec, evec);
    approxcomp(i,1) = beta;
    approxvec = approxvec + beta*evec;
end