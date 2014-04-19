function [ok, grid_bracing] = read_grid_file(filename)
grid_bracing=load(filename);%load tha matrix from .txt
[m,n]=size(grid_bracing);% determine the m*n matrix
h=find(grid_bracing==1);%find the '1' in m*n matrix
i=length(h);%calculate how many '1' in m*n matrix
j=find(grid_bracing==0);%find the '0' in m*n matrix
k=length(j);%calculate how many '0' in m*n matrix
M=i+k;%calculate how many '0'and '1' in this matrix
N=m*n;%calculate the total factors in this matrix
if M==N%determine if all the factor in matrix are '0' or '1'
    ok=num2str(M);
    disp ok;
else
    disp false;
end
end