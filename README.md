# pomelo
npm uninstall -g angular-cli
npm cache clean or npm cache verify (if npm > 5)
npm install -g @angular/cli@latest

rm -rf node_modules
npm uninstall --save-dev angular-cli
npm install --save-dev @angular/cli@latest
npm install