clc;

load a2q1group1.dat;
load a2q1group2.dat;

% different color to plot line
color=[0 0 0
0 0 1
0 1 0
0 1 1
1 0 0
1 0 1
1 1 0
1 1 1
0 .5 0
0 .75 .75
0 .75 .5
0 .5 .75
0 .5 .5
1 .75 .75
1 .75 .5
1 .5 .5
.5 0 1
.5 1 0
.5 0 0
.5 1 1
.75 0 1
.75 1 0
.75 0 0
.75 1 1
.5 .5 .5
.75 .75 .75
.75 .75 .5
.5 .75 .75
.75 .5 .75
.5 .5 .75
.5 .75 .5
.75 .5 .5
]; 

% find the eigenvalues, mean vectors, the eigenvectors of the PCA (as column vectors) 
% and the covariance matrix of each group
[evalvec1, meanvec1, evecmat1, coverage1] = pcaprelim(a2q1group1');
[evalvec2, meanvec2, evecmat2, coverage2] = pcaprelim(a2q1group2');

%% process a2q2group1
numEig1 = 3; % number of eigenvalues 
error1 = zeros(30,1); % initial error1
index = zeros(101,1); % finding index 
for j = 1:101
    index(j,1) = j-1;
end
% row index
for r1 = 1:30
    [approxcomp1,approxvec1] = pcareduce(a2q1group1(r1,:)', numEig1, meanvec1, evecmat1);   
    error1(r1) = recostrError(a2q1group1(r1,:), approxvec1);    % get reconstruct error
   
    % plot principal component
    plot(index,approxvec1,'color',color(r1,:));
    hold all;
end
title('Group1 principle components');

% plot a2q2group1 reconstruction error
figure;
plot(error1,'b.-');
title('Group1 reconstruct error');

%% process a2q2group2

numEig2 = 2;
error2 = zeros(30,1); % initial error2
index = zeros(101,1); % finding index 
for j = 1:101
    index(j,1) = j-1;
end
% row index
figure;
for r2 = 1:30
    [approxcomp2,approxvec2] = pcareduce(a2q1group2(r2,:)', numEig2, meanvec2, evecmat2);
    error2(r2) = recostrError(a2q1group2(r2,:), approxvec2);    % get reconstruct error
   
    % plot principal component
    plot(index,approxvec2,'color',color(r2,:));
    hold all;
end
title('Group2 principle components');

% plot a2q2group1 reconstruction error
figure;
plot(error2,'b.-');
title('Group2 reconstruct error');

%% process mean of two group
figure;
plot(index,meanvec1,'b',index,meanvec2,'r');
title('mean vector of two groups');
legend('Group1 mean vector','Group2 mean vector','Location','NorthWest');