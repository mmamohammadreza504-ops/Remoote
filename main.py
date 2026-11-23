import os
import logging

from telegram import Update
from telegram.ext import (
    Application,
    CommandHandler,
    ContextTypes,
)

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s %(levelname)s %(message)s",
)

TOKEN = os.environ["BOT_TOKEN"]
OWNER_ID = os.environ["OWNER_ID"]
PUBLIC_URL = os.environ["WEBHOOK_URL"].rstrip("/")
WEBHOOK_PATH = os.environ["WEBHOOK_PATH"].strip("/")
WEBHOOK_SECRET = os.environ["WEBHOOK_SECRET"]

def is_owner(update: Update) -> bool:
    user = update.effective_user
    return user is not None and str(user.id) == OWNER_ID

async def start(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if not is_owner(update):
        await update.effective_message.reply_text("دسترسی مجاز نیست.")
        return

    await update.effective_message.reply_text(
        "Dr Remote فعال شد!\n\n"
        "/help - راهنما\n"
        "/status - وضعیت ربات\n"
        "/devices - دستگاه‌های ثبت‌شده"
    )

async def help_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if not is_owner(update):
        return

    await update.effective_message.reply_text(
        "/start - شروع\n"
        "/status - وضعیت\n"
        "/devices - دستگاه‌ها"
    )

async def status(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if not is_owner(update):
        return

    await update.effective_message.reply_text(
        "Dr Remote: فعال\n"
        "Telegram Webhook: دریافت فرمان فعال\n"
        "Android: هنوز متصل نشده"
    )

async def devices(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if not is_owner(update):
        return

    await update.effective_message.reply_text(
        "هنوز دستگاهی ثبت نشده است."
    )

def main():
    port = int(os.environ.get("PORT", "10000"))

    app = Application.builder().token(TOKEN).build()

    app.add_handler(CommandHandler("start", start))
    app.add_handler(CommandHandler("help", help_command))
    app.add_handler(CommandHandler("status", status))
    app.add_handler(CommandHandler("devices", devices))

    logging.info("Starting Dr Remote webhook server")

    app.run_webhook(
        listen="0.0.0.0",
        port=port,
        url_path=WEBHOOK_PATH,
        webhook_url=f"{PUBLIC_URL}/{WEBHOOK_PATH}",
        secret_token=WEBHOOK_SECRET,
        bootstrap_retries=-1,
        allowed_updates=["message"],
    )

if __name__ == "__main__":
    main()
    
