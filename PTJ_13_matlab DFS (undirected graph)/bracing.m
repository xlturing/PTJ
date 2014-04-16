function bracing()
clc;
%% Project checkpoint #2
[ok,grid]=read_grid_file('A.txt')
% rows columns
[r,c]=size(grid);
total_vert = (r+c);
edges=zeros(total_vert);
visited=zeros(1,r+c);
predecessor=zeros(1,r+c);
vertices=cell(1,r+c);

% vertics
for i = 1:r+c
    if(i <= r)
        vertices{i} = strcat('r',num2str(i));
    else
        vertices{i} = strcat('c',num2str(i-r));
    end
end
vertices

% grid to graph
for row = 1:r;
    for col = 1:c;
        if grid(row,col)==1
            edges(row,r+col)=1;
            edges(r+col,row)=1;
        end
    end
end
edges

% DFS graph and decide whether this grid is bracing and minimal
[flag,vertices, edges, v, visited] = DFS(vertices, edges, 1, visited, 0);
if(all(visited) == 1 & flag == 1)
    disp('This grid is bracing, but not minimal');
elseif(all(visited) == 1 & flag == 0)
    disp('This grid is bracing, and minimal');
else
    disp('This grid is not bracing');
end
end

%% Project checkpoint #3 
% DFS Function implement
function [flag,vertices, edges, v, visited] = DFS(vertices, edges, v, visited, pre)
% depth first search
% flag - exist loop in this graph? 1 yes, 0 no
% vertices - vertics
% edges - adjacency graph
% v - start vertic
% visited -  remember which vertices you have already visited, 1 visited, 0
% unvisited
% pre - father vertic
visited(v) = 1;
flag = 0;
for i = 1:size(edges)
    % if there is a edge between vertic i and v, then label it and DFS from here 
    if( edges(i,v) == 1)
        if(visited(i) == 0)
            visited(i) = 1;
            %predecessor(i) = v;
            [flag,vertices, edges, v, visited]= DFS(vertices, edges, i, visited, v);
        elseif(pre~= i)
            % exist loop in this graph
            flag = 1;
        end
    end
end
end