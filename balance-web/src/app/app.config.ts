import { ApplicationConfig, ErrorHandler, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, TitleStrategy, withComponentInputBinding } from '@angular/router';

import { routes } from './app.routes';
import { AppTitleService } from './services/commons/app-title.service';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokensInterceptor } from './services/security/tokens.interceptor';
import { GlobalErrorService } from './services/commons/global-error.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withComponentInputBinding()),
    provideHttpClient(withInterceptors([tokensInterceptor])),
    {provide: TitleStrategy, useClass: AppTitleService},
    {provide: ErrorHandler, useClass: GlobalErrorService}
  ]
};
