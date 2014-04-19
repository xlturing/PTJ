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
start = 1; % DFS start vertical
predecessor(start) = -1; % set a flag
[vertices, edges, v, visited, predecessor] = DFS(vertices, edges, start, visited, predecessor);
if(all(visited) == 1 & findCircle(edges) == 1)
    disp('This grid is bracing, but not minimal');
elseif(all(visited) == 1 & findCircle(edges) == 0)
    disp('This grid is bracing, and minimal');
else
    disp('This grid is not bracing');
end
end

%% Project checkpoint #3 
% DFS Function implement
function [vertices, edges, v, visited, predecessor] = DFS(vertices, edges, v, visited, predecessor)
% depth first search
% flag - exist loop in this graph? 1 yes, 0 no
% vertices - vertics
% edges - adjacency graph
% v - start vertic
% visited -  remember which vertices you have already visited, 1 visited, 0
% unvisited
% predecessor - father vertic
visited(v) = 1;
for i = 1:size(edges)
    % if there is a edge between vertic i and v, then label it and DFS from here 
    if( edges(v,i) == 1)
        if(visited(i) == 0)
            visited(i) = 1;
            visited(v) = 1;
            predecessor(i) = v;
            [vertices, edges, v, visited, predecessor]= DFS(vertices, edges, i, visited, predecessor);
        end
    end
end
end

function [flag] = findCircle(edges)
% find the graph wether exist circle
% edges - adjacency graph
% flag - 1 if exist circle, otherwise 0
  flag = 0;
  n = size(edges);
  pre = zeros(n,n);
  while(isequal(pre,edges)==0)
      pre = edges;
      for i = 1 : n
        pos_one = find(edges(i,:)); % find the node which degree is 1
        if(length(pos_one) == 1) 
            edges(:,pos_one) = 0; % and delete this node
            edges(pos_one,:) = 0;
        end
      end
  end
  if(any(any(edges)) == 1) % if there are some nodes exist, then this graph have circle
      flag = 1;
  end
end
