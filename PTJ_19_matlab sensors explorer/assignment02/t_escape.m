% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 20th.
% Functionality: returns the change of thief's coordinates while he is
% moving to the escape spot.

function [dx, dy, warn_T] = t_escape(t, crd_T, x_e, y_e, crd_C, size, r_thief)
warn_T = 0; % Everything's ok; game goes on
crd_T_inst = cell2mat(crd_T(t-1));
x_T = crd_T_inst(1);
y_T = crd_T_inst(2);

crd_C_inst = cell2mat(crd_C(:, t-1)); % All cops coords at previous step
x_C = crd_C_inst(:,1); % All cops' x coord
y_C = crd_C_inst(:,2); % All cops' y coord
C_dist = sqrt((x_C-x_T).^2 + (y_C-y_T).^2); % All cop-to-thief distances
C_found = find(C_dist == min(C_dist)); % Nearest cop(s)

if min(C_dist) <= r_thief % Finds cop
    [dx, dy, warn_T] = find_cop(size, x_T, y_T, C_found, x_C, y_C);
else % No cop
    if x_e > x_T
            dx = 1;
            dy = 0;
        elseif x_e < x_T
            dx = -1;
            dy = 0;
        elseif y_e > y_T
            dx = 0;
            dy = 1;
        elseif y_e < y_T
            dx = 0;
            dy = -1;
        else
            warn_T = 3; % Escapes successfully!!!
            dx = 0;
            dy = 0;
     end
end
end