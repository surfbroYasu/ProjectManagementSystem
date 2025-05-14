import { defineConfig } from 'vite';

export default defineConfig({
  root: '.',
  build: {
    outDir: '../src/main/resources/static/assets',
    emptyOutDir: true,
    assetsDir: '.',
    rollupOptions: {
      input: {
        codeblock: './src/codeblock.js'
      },
      output: {
        entryFileNames: (chunkInfo) => {
          if (chunkInfo.name === 'codeblock') return 'codeblock/codeblock.js';
          return '[name].js';
        },
        assetFileNames: (chunkInfo) => {
          if (chunkInfo.name === 'codeblock.css' || chunkInfo.name.includes('color-brewer')) {
            return 'codeblock/codeblock.css';
          }
          return '[name][extname]';
        }
        
        
      }
    }
  }
});
