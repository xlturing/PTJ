function [pcomp,pvec] = pcatotal(matrix,size,approxnum,meanvec,evecmat)
% a function that groups all of the pcaaprox() results together
% Inputs: matrix - group data
%         size - number of signals in the group
%         - rest of the input variables are the same 
%         as those used in pcaaprox
% Outputs: pcomp - a matrix containing all of the components
%                - the components of each signal are stored in column
%                  vectors
%          pvec - a matrix containing all of the approximations of the new data
%                 column vectors
pcomp=[];
pvec=[];
for i = 1:size
    data = matrix(:,i);
    [comp,vec] = pcaapprox(data,approxnum,meanvec,evecmat);
    pcomp=[pcomp,comp];
    pvec = [pvec,vec];
end