function [efM] = ToReducedRowEchelonForm(M)
% convert mattrix M to echelon form
% M - the matrix need to convert
% efM - result
lead = 1;
rowCount = size(M,1);
columnCount = size(M,2);
for r = 1:rowCount
    if (columnCount <= lead)
        break;
    else
        i = r;
    end
    while (M(i,lead) == 0)
        i = i + 1;
        if rowCount == i
            i = r;
            lead = lead + 1;
            if (columnCount == lead)
                break;
            end
        end
    end
    M([i;r],:)=M([r;i],:);
    if (M(r, lead) ~= 0)
        M(r,:) = M(r,:)./M(r, lead);
    end
    for i = 1:rowCount
        if i ~= r
            temp =  M(r,1:columnCount).*M(i, lead);
            M(i,1:columnCount) = M(i,1:columnCount) - temp(1:columnCount);
        end
    end
    lead = lead + 1;
end
efM = M;
end