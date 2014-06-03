function f_number=f_select(x,y,x1,y1,D,success)
% f_select function is used for choosing a proporate function to calcute the 
% thief's continuing moving method. this function will use the initinal position and 
% the direction position to calculate the distance between them and compare 
% this value with the input D. Then, return the f_number(function number) 
% after combine the comparing result and the success.


% distance between(x,y) (x1,y1) is larger than the given D and scuuess is 0
if(sqrt(((x-x1)^2+(y-y1)^2))>D && success==0)
    % return the function NO.1
    f_number=1;
% distance between(x,y) (x1,y1) is smaller or equal to the given D and 
% scuuess is 0
elseif(sqrt(((x-x1)^2+(y-y1)^2))<=D && success==0)
    % return the function NO.2
    f_number=2;
% success equals to 1 and has no relation wo the ditance.
elseif success==1
    % return the function NO.2
    f_number=3;
end
end