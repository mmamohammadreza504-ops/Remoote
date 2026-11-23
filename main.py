import os
import logging
from telegram import Update
from telegram.ext import (
    Application,
    CommandHandler,
    ContextTypes,
)

logging.basicConfig(
    format="%(asctime)s - %(levelname)s - %(message)s",
    level=logging.INFO,
)

TOKEN = os.getenv("BOT_TOKEN")
OWNER_ID = os.getenv("OWNER_ID")


def is_owner(update: Update) -> bool:
    return (
        OWNER_ID is not None
        and update.effective_user is not None
        and str(update.effective_user.id) == OWNER_ID
    )


async def start(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if not is_owner(update):
        await update.message.reply_text(
            "دسترسی مجاز نیست."
        )
        return

    await update.message.reply_text(
        "خوش آمدی به Dr Remote!\n"
        "هسته ربات با موفقیت فعال است.\n\n"
        "/help - راهنما\n"
        "/status - وضعیت ربات\n"
        "/devices - دستگاه‌های ثبت‌شده"
    )


async def help_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if not is_owner(update):
        await update.message.reply_text("دسترسی مجاز نیست.")
        return

    await update.message.reply_text(
        "راهنمای Dr Remote\n"
        "/start - شروع\n"
        "/status - وضعیت سیستم\n"
        "/devices - دستگاه‌های ثبت‌شده"
    )


async def status(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if not is_owner(update):
        await update.message.reply_text("دسترسی مجاز نیست.")
        return

    await update.message.reply_text(
        "Dr Remote: فعال\n"
        "Telegram API: متصل\n"
        "Backend: در حال اجرا"
    )


async def devices(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if not is_owner(update):
        await update.message.reply_text("دسترسی مجاز نیست.")
        return

    await update.message.reply_text(
        "هنوز دستگاهی ثبت نشده است."
    )


def main():
    if not TOKEN or not OWNER_ID:
        raise RuntimeError(
            "BOT_TOKEN و OWNER_ID باید تنظیم شوند."
        )

    app = Application.builder().token(TOKEN).build()

    app.add_handler(CommandHandler("start", start))
    app.add_handler(CommandHandler("help", help_command))
    app.add_handler(CommandHandler("status", status))
    app.add_handler(CommandHandler("devices", devices))

    logging.info("Dr Remote is starting...")
    app.run_polling()


if __name__ == "__main__":
    main()
  
