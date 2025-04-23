import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig({
  root: '.',
  build: {
    outDir: '../src/main/resources/static/js/bundle',
    emptyOutDir: true,
    assetsDir: '.',
    rollupOptions: {
      input: './src/codeblock.js', 
      output: {
        entryFileNames: 'codeblock.js', 
        assetFileNames: (chunkInfo) => {
          // CSSだけは固定名にする
          if (chunkInfo.name === 'color-brewer.css') return 'highlight.css';
          return '[name][extname]';
        }
      }
    }
  }
});
