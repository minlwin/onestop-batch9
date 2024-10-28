import { ApplicationConfig, ErrorHandler, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, TitleStrategy, withComponentInputBinding } from '@angular/router';

import { routes } from './app.routes';
import { AppTitleService } from './services/commons/app-title.service';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenAccessInterceptor } from './services/security/token-access.interceptor';
import { GlobalErrorService } from './services/commons/global-error.service';
import { tokenRefreshInterceptor } from './services/security/token-refresh.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    {provide: TitleStrategy, useClass: AppTitleService},
    {provide: ErrorHandler, useClass: GlobalErrorService},
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withComponentInputBinding()),
    provideHttpClient(withInterceptors([tokenAccessInterceptor, tokenRefreshInterceptor])),
  ]
};
