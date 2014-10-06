function [recostrError] = recostrError(origData, approVect)
% Function [recostrError] = recostrError(orgiData, approVect)
% get reconstruct error of each row data
% origData - origenal data of one row
% approVect - approximated vector

sum1 = sum(origData);   % original data sum
sum2 = sum(approVect);   % approximated data sum

recostrError = abs(sum1-sum2)/sum1;