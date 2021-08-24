@echo off

title GIT one key commit
color 3
echo Current directory %cd%
echo;

echo Operation: git add .
git add .
echo;

set /p declation= please input commit msg:
git commit -m "%declation%"
echo;

git push origin develop
echo;
